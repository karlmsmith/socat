package gov.noaa.pmel.dashboard.actions;

import gov.noaa.pmel.dashboard.datatype.KnownDataTypes;
import gov.noaa.pmel.dashboard.dsg.DsgMetadata;
import gov.noaa.pmel.dashboard.dsg.StdUserDataArray;
import gov.noaa.pmel.dashboard.handlers.CheckerMessageHandler;
import gov.noaa.pmel.dashboard.qc.RowColumn;
import gov.noaa.pmel.dashboard.shared.DashboardDatasetData;
import gov.noaa.pmel.dashboard.shared.DashboardUtils;
import gov.noaa.pmel.dashboard.shared.DataQCFlag;
import gov.noaa.pmel.dashboard.shared.DataQCFlag.Severity;

import java.util.HashSet;

/**
 * Class for interpreting, standardizing, and checking user-provided data.
 *
 * @author Karl Smith
 */
public class DatasetChecker {

    private CheckerMessageHandler msgHandler;
    private KnownDataTypes knownUserDataTypes;

    /**
     * @param userDataTypes
     *         all known user data types
     * @param checkerMessageHandler
     *         handler for automated data checker messages
     *
     * @throws IllegalArgumentException
     *         if either argument is null, or if there are no user data types given
     */
    public DatasetChecker(KnownDataTypes userDataTypes, CheckerMessageHandler checkerMessageHandler)
            throws IllegalArgumentException {
        if ( (userDataTypes == null) || userDataTypes.isEmpty() )
            throw new IllegalArgumentException("no known user data types");
        if ( checkerMessageHandler == null )
            throw new IllegalArgumentException("no message handler given to the dataset checker");
        knownUserDataTypes = userDataTypes;
        msgHandler = checkerMessageHandler;
    }

    /**
     * Interprets the data string representations and standardizes, if required, these data values
     * for given dataset.  Performs the automated data checks on these data values.  Saves the messages
     * generated from these steps and assigns the automated data checker WOCE flags from these messages.
     * <p>
     * The given dataset object is updated with the set of checker QC flags, the set of user-provided
     * QC flags, the number of rows with errors (not marked by the PI), the number of rows with warnings
     * (not marked by the PI), and the current data check status.
     * <p>
     * The given metadata object, if not null, it is updated with values that can be derived from the data:
     * western-most longitude, eastern-most longitude, southern-most latitude, northern-most latitude,
     * start time, and end time.
     *
     * @param dataset
     *         dataset data to check; various fields will be updated by this method
     * @param metadata
     *         metadata to update; can be null
     *
     * @return standardized user data array of checked values
     *
     * @throws IllegalArgumentException
     *         if there are no data values,
     *         if a data column description is not a known user data type,
     *         if a required unit conversion is not supported,
     *         if a standardizer for a given data type is not known,
     *         if ....
     */
    public StdUserDataArray standardizeDataset(DashboardDatasetData dataset, DsgMetadata metadata)
            throws IllegalArgumentException {
        // Generate array of standardized data objects
        StdUserDataArray stdUserData = new StdUserDataArray(dataset, knownUserDataTypes);

        // Check for missing lon/lat/time
        Double[] sampleTimes = stdUserData.checkMissingLonLatTime();

        // Check that the data is ordered in time; speeds and time gaps are not excessive.
        // Generate errors where this is not the case.
        if ( sampleTimes != null )
            stdUserData.checkDataOrder(sampleTimes);

        // Bounds check the standardized data values
        stdUserData.checkBounds();

        // Any metadata values given in data columns must be consistent (constant or missing)
        stdUserData.checkMetadataTypeValues();

        // TODO: Perform any other data checks?

        // Save the messages accumulated in stdUserData.
        // Assigns the StdUserData WOCE_AUTOCHECK data column with the checker-generated data QC flags.
        // Assigns the DashboardDataset sets of checker-generated and user-provided data QC flags.
        msgHandler.processCheckerMessages(dataset, stdUserData);

        // Get the indices values the PI marked as bad or questionable.
        boolean hasCriticalError = false;
        HashSet<RowColumn> userErrs = new HashSet<RowColumn>();
        HashSet<RowColumn> userWarns = new HashSet<RowColumn>();
        for (DataQCFlag wtype : dataset.getUserFlags()) {
            if ( Severity.CRITICAL.equals(wtype.getSeverity()) ) {
                hasCriticalError = true;
                userErrs.add(new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex()));
            }
            else if ( Severity.ERROR.equals(wtype.getSeverity()) ) {
                userErrs.add(new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex()));
            }
            else if ( Severity.WARNING.equals(wtype.getSeverity()) ) {
                userWarns.add(new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex()));
            }
        }
        // Get the indices of data rows the automated data checker
        // found having errors not not detected by the PI.
        HashSet<Integer> errRows = new HashSet<Integer>();
        for (DataQCFlag wtype : dataset.getCheckerFlags()) {
            if ( Severity.CRITICAL.equals(wtype.getSeverity()) ) {
                hasCriticalError = true;
                RowColumn rowCol = new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex());
                if ( !userErrs.contains(rowCol) )
                    errRows.add(wtype.getRowIndex());
            }
            if ( Severity.ERROR.equals(wtype.getSeverity()) ) {
                RowColumn rowCol = new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex());
                if ( !userErrs.contains(rowCol) )
                    errRows.add(wtype.getRowIndex());
            }
        }
        // Get the indices of data rows the automated data checker
        // found having only warnings but not detected by the PI.
        HashSet<Integer> warnRows = new HashSet<Integer>();
        for (DataQCFlag wtype : dataset.getCheckerFlags()) {
            if ( Severity.WARNING.equals(wtype.getSeverity()) ) {
                RowColumn rowCol = new RowColumn(wtype.getRowIndex(), wtype.getColumnIndex());
                Integer rowIdx = wtype.getRowIndex();
                if ( !(userErrs.contains(rowCol) || userWarns.contains(rowCol) || errRows.contains(rowIdx)) )
                    warnRows.add(rowIdx);
            }
        }

        int numErrorRows = errRows.size();
        int numWarnRows = warnRows.size();

        dataset.setNumErrorRows(numErrorRows);
        dataset.setNumWarnRows(numWarnRows);

        // Assign the data-check status message using the results of the sanity check
        if ( hasCriticalError ) {
            dataset.setDataCheckStatus(DashboardUtils.CHECK_STATUS_UNACCEPTABLE);
        }
        else if ( numErrorRows > 0 ) {
            dataset.setDataCheckStatus(DashboardUtils.CHECK_STATUS_ERRORS_PREFIX +
                    Integer.toString(numErrorRows) + " errors");
        }
        else if ( numWarnRows > 0 ) {
            dataset.setDataCheckStatus(DashboardUtils.CHECK_STATUS_WARNINGS_PREFIX +
                    Integer.toString(numWarnRows) + " warnings");
        }
        else {
            dataset.setDataCheckStatus(DashboardUtils.CHECK_STATUS_ACCEPTABLE);
        }

        if ( metadata != null ) {
            for (RowColumn rowCol : userErrs) {
                errRows.add(rowCol.getRow());
            }
            Double[] sampleLongitudes = stdUserData.getSampleLongitudes();
            Double[] sampleLatitudes = stdUserData.getSampleLatitudes();
            metadata.assignLonLatTimeLimits(sampleLongitudes, sampleLatitudes, sampleTimes, errRows);
        }

        return stdUserData;
    }

}
