/**
 * 
 */
package gov.noaa.pmel.dashboard.standardize;

import java.util.Date;

import gov.noaa.pmel.dashboard.shared.DataColumnType;

/**
 * @author Karl Smith
 */
public class DateStandardizer extends ValueStandardizer {

	public DateStandardizer(DataColumnType dtype) throws IllegalArgumentException {
		super(dtype);
		// TODO:
	}

	@Override
	public Date getStandardValue(String strVal) throws IllegalArgumentException {
		// TODO:
		throw new IllegalArgumentException("not yet implemented");
	}

}
