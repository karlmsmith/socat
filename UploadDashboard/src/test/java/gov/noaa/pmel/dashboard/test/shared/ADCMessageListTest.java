package gov.noaa.pmel.dashboard.test.shared;

import gov.noaa.pmel.dashboard.shared.ADCMessage;
import gov.noaa.pmel.dashboard.shared.ADCMessageList;
import gov.noaa.pmel.dashboard.shared.DashboardUtils;
import gov.noaa.pmel.dashboard.shared.DataQCFlag.Severity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for method of {@link ADCMessageList}.
 *
 * @author Karl Smith
 */
public class ADCMessageListTest {

    /**
     * Test method for {@link ADCMessageList#getUsername()} and {@link ADCMessageList#setUsername(String)}.
     */
    @Test
    public void testGetSetUsername() {
        final String myUsername = "SocatUser";
        ADCMessageList msgList = new ADCMessageList();
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getUsername());
        msgList.setUsername(myUsername);
        assertEquals(myUsername, msgList.getUsername());
        msgList.setUsername(null);
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getUsername());
    }

    /**
     * Test method for {@link ADCMessageList#getDatasetId()} and {@link ADCMessageList#setDatasetId(String)}.
     */
    @Test
    public void testGetSetDatasetId() {
        final String myExpocode = "XXXX20140204";
        ADCMessageList msgList = new ADCMessageList();
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getDatasetId());
        msgList.setDatasetId(myExpocode);
        assertEquals(myExpocode, msgList.getDatasetId());
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getUsername());
        msgList.setDatasetId(null);
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getDatasetId());
    }

    /**
     * Test method for {@link ADCMessageList#getSummaries()} and {@link ADCMessageList#setSummaries(ArrayList)}.
     */
    @Test
    public void testGetSetSummaries() {
        final ArrayList<String> mySummaries = new ArrayList<String>(Arrays.asList(
                "3 errors of type: P_equ unreasonably large",
                "5 errors of type: calculated ship speed unreasonably large",
                "20 warnings of type: P_equ unusually large"));
        ADCMessageList msgList = new ADCMessageList();
        assertEquals(0, msgList.getSummaries().size());
        msgList.setSummaries(mySummaries);
        assertEquals(mySummaries, msgList.getSummaries());
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getDatasetId());
        assertEquals(DashboardUtils.STRING_MISSING_VALUE, msgList.getUsername());
        msgList.setSummaries(null);
        assertEquals(0, msgList.getSummaries().size());
    }

    /**
     * Test method for {@link ADCMessageList#hashCode()} and {@link ADCMessageList#equals(Object)}.
     */
    @Test
    public void testHashCodeEquals() {
        final String myUsername = "SocatUser";
        final String myExpocode = "XXXX20140204";
        final ArrayList<String> mySummaries = new ArrayList<String>(Arrays.asList(
                "3 errors of type: P_equ unreasonably large",
                "5 errors of type: calculated ship speed unreasonably large",
                "20 warnings of type: P_equ unusually large"));
        final Severity mySeverity = Severity.ERROR;
        final int myRowNum = 25;
        final int myColNum = 8;
        final String myColName = "P_atm";
        final String myExplanation = "value exceeds the upper limit of questionable values";

        ADCMessage myMsg = new ADCMessage();
        myMsg.setSeverity(mySeverity);
        myMsg.setRowNumber(myRowNum);
        myMsg.setColNumber(myColNum);
        myMsg.setColName(myColName);
        myMsg.setGeneralComment(myExplanation);
        myMsg.setDetailedComment(myExplanation);

        ADCMessage otherMsg = new ADCMessage();
        otherMsg.setSeverity(mySeverity);
        otherMsg.setRowNumber(myRowNum);
        otherMsg.setColNumber(myColNum);
        otherMsg.setColName(myColName);
        otherMsg.setGeneralComment(myExplanation);
        otherMsg.setDetailedComment(myExplanation);
        assertEquals(myMsg, otherMsg);

        ADCMessageList msgList = new ADCMessageList();
        assertFalse(msgList.equals(null));
        assertFalse(msgList.equals(myExpocode));

        ADCMessageList other = new ADCMessageList();
        assertEquals(msgList.hashCode(), other.hashCode());
        assertTrue(msgList.equals(other));

        msgList.setUsername(myUsername);
        assertNotEquals(msgList.hashCode(), other.hashCode());
        assertFalse(msgList.equals(other));
        other.setUsername(myUsername);
        assertEquals(msgList.hashCode(), other.hashCode());
        assertTrue(msgList.equals(other));

        msgList.setDatasetId(myExpocode);
        assertNotEquals(msgList.hashCode(), other.hashCode());
        assertFalse(msgList.equals(other));
        other.setDatasetId(myExpocode);
        assertEquals(msgList.hashCode(), other.hashCode());
        assertTrue(msgList.equals(other));

        msgList.setSummaries(mySummaries);
        assertNotEquals(msgList.hashCode(), other.hashCode());
        assertFalse(msgList.equals(other));
        other.setSummaries(mySummaries);
        assertEquals(msgList.hashCode(), other.hashCode());
        assertTrue(msgList.equals(other));

        msgList.add(myMsg);
        assertNotEquals(msgList.hashCode(), other.hashCode());
        assertFalse(msgList.equals(other));
        other.add(otherMsg);
        assertEquals(msgList.hashCode(), other.hashCode());
        assertTrue(msgList.equals(other));

        // myMsg same as otherMsg
        assertFalse(msgList.add(otherMsg));
    }

}
