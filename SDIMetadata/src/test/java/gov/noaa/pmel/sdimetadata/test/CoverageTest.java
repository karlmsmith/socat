package gov.noaa.pmel.sdimetadata.test;

import gov.noaa.pmel.sdimetadata.Coverage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoverageTest {

    private static final double DELTA = 1.0E-6;
    private static final double WESTERN_LONGITUDE = 146.23;
    private static final double EASTERN_LONGITUDE = -120.45;
    private static final double SOUTHERN_LATITUDE = 15.36;
    private static final double NORTHERN_LATITUDE = 45.03;
    private static final double EARLIEST_DATA_TIME = 123456.78;
    private static final double LATEST_DATA_TIME = 234567.89;
    private static final double START_TIME = 123400.00;
    private static final double END_TIME = 234600.00;

    @Test
    public void testGetSetWesternLongitude() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setWesternLongitude(WESTERN_LONGITUDE);
        assertEquals(WESTERN_LONGITUDE, coverage.getWesternLongitude(), DELTA);
        coverage.setWesternLongitude(null);
        assertTrue(coverage.getWesternLongitude().isNaN());
    }

    @Test
    public void testGetSetEasternLongitude() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getEasternLongitude().isNaN());
        coverage.setEasternLongitude(EASTERN_LONGITUDE);
        assertEquals(EASTERN_LONGITUDE, coverage.getEasternLongitude(), DELTA);
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setEasternLongitude(null);
        assertTrue(coverage.getEasternLongitude().isNaN());
    }

    @Test
    public void testGetSetSouthernLatitude() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getSouthernLatitude().isNaN());
        coverage.setSouthernLatitude(SOUTHERN_LATITUDE);
        assertEquals(SOUTHERN_LATITUDE, coverage.getSouthernLatitude(), DELTA);
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setSouthernLatitude(null);
        assertTrue(coverage.getSouthernLatitude().isNaN());
    }

    @Test
    public void testGetSetNorthernLatitude() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getNorthernLatitude().isNaN());
        coverage.setNorthernLatitude(NORTHERN_LATITUDE);
        assertEquals(NORTHERN_LATITUDE, coverage.getNorthernLatitude(), DELTA);
        assertTrue(coverage.getSouthernLatitude().isNaN());
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setNorthernLatitude(null);
        assertTrue(coverage.getNorthernLatitude().isNaN());
    }

    @Test
    public void testGetSetEarliestDataTime() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getEarliestDataTime().isNaN());
        coverage.setEarliestDataTime(EARLIEST_DATA_TIME);
        assertEquals(EARLIEST_DATA_TIME, coverage.getEarliestDataTime(), DELTA);
        assertTrue(coverage.getNorthernLatitude().isNaN());
        assertTrue(coverage.getSouthernLatitude().isNaN());
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setEarliestDataTime(null);
        assertTrue(coverage.getEarliestDataTime().isNaN());
    }

    @Test
    public void testGetSetLatestDataTime() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getLatestDataTime().isNaN());
        coverage.setLatestDataTime(LATEST_DATA_TIME);
        assertEquals(LATEST_DATA_TIME, coverage.getLatestDataTime(), DELTA);
        assertTrue(coverage.getEarliestDataTime().isNaN());
        assertTrue(coverage.getNorthernLatitude().isNaN());
        assertTrue(coverage.getSouthernLatitude().isNaN());
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setLatestDataTime(null);
        assertTrue(coverage.getLatestDataTime().isNaN());
    }

    @Test
    public void testGetSetStartTime() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getStartTime().isNaN());
        coverage.setStartTime(START_TIME);
        assertEquals(START_TIME, coverage.getStartTime(), DELTA);
        assertTrue(coverage.getLatestDataTime().isNaN());
        assertTrue(coverage.getEarliestDataTime().isNaN());
        assertTrue(coverage.getNorthernLatitude().isNaN());
        assertTrue(coverage.getSouthernLatitude().isNaN());
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setStartTime(null);
        assertTrue(coverage.getStartTime().isNaN());
    }

    @Test
    public void testGetSetEndTime() {
        Coverage coverage = new Coverage();
        assertTrue(coverage.getEndTime().isNaN());
        coverage.setEndTime(END_TIME);
        assertEquals(END_TIME, coverage.getEndTime(), DELTA);
        assertTrue(coverage.getStartTime().isNaN());
        assertTrue(coverage.getLatestDataTime().isNaN());
        assertTrue(coverage.getEarliestDataTime().isNaN());
        assertTrue(coverage.getNorthernLatitude().isNaN());
        assertTrue(coverage.getSouthernLatitude().isNaN());
        assertTrue(coverage.getEasternLongitude().isNaN());
        assertTrue(coverage.getWesternLongitude().isNaN());
        coverage.setEndTime(null);
        assertTrue(coverage.getEndTime().isNaN());
    }

    @Test
    public void testHashCodeEquals() {
        Coverage first = new Coverage();
        assertFalse(first.equals(null));
        assertFalse(first.equals(WESTERN_LONGITUDE));

        Coverage second = new Coverage();
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setWesternLongitude(WESTERN_LONGITUDE);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setWesternLongitude(WESTERN_LONGITUDE);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setEasternLongitude(EASTERN_LONGITUDE);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setEasternLongitude(EASTERN_LONGITUDE);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setSouthernLatitude(SOUTHERN_LATITUDE);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setSouthernLatitude(SOUTHERN_LATITUDE);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setNorthernLatitude(NORTHERN_LATITUDE);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setNorthernLatitude(NORTHERN_LATITUDE);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setEarliestDataTime(EARLIEST_DATA_TIME);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setEarliestDataTime(EARLIEST_DATA_TIME);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setLatestDataTime(LATEST_DATA_TIME);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setLatestDataTime(LATEST_DATA_TIME);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setStartTime(START_TIME);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setStartTime(START_TIME);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));

        first.setEndTime(END_TIME);
        assertFalse(first.hashCode() == second.hashCode());
        assertFalse(first.equals(second));
        second.setEndTime(END_TIME);
        assertTrue(first.hashCode() == second.hashCode());
        assertTrue(first.equals(second));
    }

}
