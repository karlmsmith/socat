/**
 *
 */
package gov.noaa.pmel.dashboard.test.shared;

import gov.noaa.pmel.dashboard.shared.Crossover;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for method of {@link Crossover}
 *
 * @author Karl Smith
 */
public class CrossoverTest {

    private static final Double minDist = 23.56;
    private static final String firstExpo = "AAAA19951201";
    private static final Integer firstRowNum = 47;
    private static final Double firstLon = 125.250;
    private static final Double firstLat = 45.320;
    private static final Long firstTime = Math.round(System.currentTimeMillis() / 1000.0);
    private static final Long firstMinTime = firstTime - (20L * 24L * 60L * 60L);
    private static final Long firstMaxTime = firstTime + (5L * 24L * 60L * 60L);
    private static final String secondExpo = "BBBB19951207";
    private static final Integer secondRowNum = 123;
    private static final Double secondLon = 125.255;
    private static final Double secondLat = 45.315;
    private static final Long secondTime = firstTime - (4L * 60L * 60L);
    private static final Long secondMinTime = secondTime - (10L * 24L * 60L * 60L);
    private static final Long secondMaxTime = secondTime + (15L * 24L * 60L * 60L);

    /**
     * Test method for {@link Crossover#getDatasetIds()} and {@link Crossover#setDatasetIds(String[])}.
     */
    @Test
    public void testGetSetDatasetIds() {
        Crossover cross = new Crossover();
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setDatasetIds(new String[] { firstExpo, secondExpo });
        expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertEquals(firstExpo, expos[0]);
        assertEquals(secondExpo, expos[1]);

        cross.setDatasetIds(null);
        expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);
    }

    /**
     * Test method for {@link Crossover#getMinDistance()} and {@link Crossover#setMinDistance(Double)}.
     */
    @Test
    public void testGetSetMinDistance() {
        Crossover cross = new Crossover();
        assertNull(cross.getMinDistance());

        cross.setMinDistance(minDist);
        assertEquals(cross.getMinDistance(), minDist, 1.0E-7);

        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setMinDistance(null);
        assertNull(cross.getMinDistance());
    }

    /**
     * Test method for {@link Crossover#getRowNumsAtMin()} and {@link Crossover#setRowNumsAtMin(Integer[])}.
     */
    @Test
    public void testGetSetRowNumsAtMin() {
        Crossover cross = new Crossover();
        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);

        cross.setRowNumsAtMin(new Integer[] { firstRowNum, secondRowNum });
        rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertEquals(firstRowNum, rowNums[0]);
        assertEquals(secondRowNum, rowNums[1]);

        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setRowNumsAtMin(null);
        rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
    }

    /**
     * Test method for {@link Crossover#getLonsAtMin()} and {@link Crossover#setLonsAtMin(Double[])}.
     */
    @Test
    public void testGetSetLonsAtMin() {
        Crossover cross = new Crossover();
        Double[] lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);

        cross.setLonsAtMin(new Double[] { firstLon, secondLon });
        lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertEquals(firstLon, lons[0]);
        assertEquals(secondLon, lons[1]);

        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setLonsAtMin(null);
        lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);
    }

    /**
     * Test method for {@link Crossover#getLatsAtMin()} and {@link Crossover#setLatsAtMin(Double[])}.
     */
    @Test
    public void testGetSetLatsAtMin() {
        Crossover cross = new Crossover();
        Double[] lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertNull(lats[0]);
        assertNull(lats[1]);

        cross.setLatsAtMin(new Double[] { firstLat, secondLat });
        lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertEquals(firstLat, lats[0]);
        assertEquals(secondLat, lats[1]);

        Double[] lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);
        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setLatsAtMin(null);
        lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertNull(lats[0]);
        assertNull(lats[1]);
    }

    /**
     * Test method for {@link Crossover#getTimesAtMin()} and {@link Crossover#setTimesAtMin(Long[])}.
     */
    @Test
    public void testGetSetTimesAtMin() {
        Crossover cross = new Crossover();
        Long[] times = cross.getTimesAtMin();
        assertEquals(2, times.length);
        assertNull(times[0]);
        assertNull(times[1]);

        cross.setTimesAtMin(new Long[] { firstTime, secondTime });
        times = cross.getTimesAtMin();
        assertEquals(2, times.length);
        assertEquals(firstTime, times[0]);
        assertEquals(secondTime, times[1]);

        Double[] lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertNull(lats[0]);
        assertNull(lats[1]);
        Double[] lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);
        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setTimesAtMin(null);
        times = cross.getTimesAtMin();
        assertEquals(2, times.length);
        assertNull(times[0]);
        assertNull(times[1]);
    }

    /**
     * Test method for {@link Crossover#getDatasetMinTimes()} and {@link Crossover#setDatasetMinTimes(Long[])}.
     */
    @Test
    public void testGetDatasetMinTimes() {
        Crossover cross = new Crossover();
        Long[] minTimes = cross.getDatasetMinTimes();
        assertEquals(2, minTimes.length);
        assertNull(minTimes[0]);
        assertNull(minTimes[1]);

        cross.setDatasetMinTimes(new Long[] { firstMinTime, secondMinTime });
        minTimes = cross.getDatasetMinTimes();
        assertEquals(2, minTimes.length);
        assertEquals(firstMinTime, minTimes[0]);
        assertEquals(secondMinTime, minTimes[1]);

        Long[] times = cross.getTimesAtMin();
        assertEquals(2, times.length);
        assertNull(times[0]);
        assertNull(times[1]);
        Double[] lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertNull(lats[0]);
        assertNull(lats[1]);
        Double[] lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);
        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setDatasetMinTimes(null);
        minTimes = cross.getDatasetMinTimes();
        assertEquals(2, minTimes.length);
        assertNull(minTimes[0]);
        assertNull(minTimes[1]);
    }

    /**
     * Test method for {@link Crossover#getDatasetMaxTimes()}
     * and {@link Crossover#setDatasetMaxTimes(Long[])}.
     */
    @Test
    public void testGetDatasetMaxTimes() {
        Crossover cross = new Crossover();
        Long[] maxTimes = cross.getDatasetMaxTimes();
        assertEquals(2, maxTimes.length);
        assertNull(maxTimes[0]);
        assertNull(maxTimes[1]);

        cross.setDatasetMaxTimes(new Long[] { firstMaxTime, secondMaxTime });
        maxTimes = cross.getDatasetMaxTimes();
        assertEquals(2, maxTimes.length);
        assertEquals(firstMaxTime, maxTimes[0]);
        assertEquals(secondMaxTime, maxTimes[1]);

        Long[] minTimes = cross.getDatasetMinTimes();
        assertEquals(2, minTimes.length);
        assertNull(minTimes[0]);
        assertNull(minTimes[1]);
        Long[] times = cross.getTimesAtMin();
        assertEquals(2, times.length);
        assertNull(times[0]);
        assertNull(times[1]);
        Double[] lats = cross.getLatsAtMin();
        assertEquals(2, lats.length);
        assertNull(lats[0]);
        assertNull(lats[1]);
        Double[] lons = cross.getLonsAtMin();
        assertEquals(2, lons.length);
        assertNull(lons[0]);
        assertNull(lons[1]);
        Integer[] rowNums = cross.getRowNumsAtMin();
        assertEquals(2, rowNums.length);
        assertNull(rowNums[0]);
        assertNull(rowNums[1]);
        assertNull(cross.getMinDistance());
        String[] expos = cross.getDatasetIds();
        assertEquals(2, expos.length);
        assertNull(expos[0]);
        assertNull(expos[1]);

        cross.setDatasetMaxTimes(null);
        maxTimes = cross.getDatasetMaxTimes();
        assertEquals(2, maxTimes.length);
        assertNull(maxTimes[0]);
        assertNull(maxTimes[1]);
    }

    /**
     * Test method for {@link Crossover#hashCode()}, {@link Crossover#equals(Object)},
     * and {@link Crossover#compareTo(Crossover)}
     */
    @Test
    public void testHashCodeEquals() {
        Crossover first = new Crossover();
        assertFalse(first.equals(null));
        assertFalse(first.equals(firstExpo));

        Crossover second = new Crossover();
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setDatasetIds(new String[] { firstExpo, secondExpo });
        assertNotEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setDatasetIds(new String[] { firstExpo, secondExpo });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setMinDistance(new Double(minDist));
        // hashCode ignores floating-point values
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setMinDistance(new Double(minDist));
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setRowNumsAtMin(new Integer[] { firstRowNum, secondRowNum });
        assertNotEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setRowNumsAtMin(new Integer[] { firstRowNum, secondRowNum });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setLonsAtMin(new Double[] { firstLon, secondLon });
        // hashCode ignores floating-point values
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setLonsAtMin(new Double[] { firstLon, secondLon });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setLatsAtMin(new Double[] { firstLat, secondLat });
        // hashCode ignores floating-point values
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setLatsAtMin(new Double[] { firstLat, secondLat });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setTimesAtMin(new Long[] { firstTime, secondTime });
        assertNotEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        second.setTimesAtMin(new Long[] { firstTime, secondTime });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setDatasetMinTimes(new Long[] { firstMinTime, secondMinTime });
        assertNotEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setDatasetMinTimes(new Long[] { firstMinTime, secondMinTime });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setDatasetMaxTimes(new Long[] { firstMaxTime, secondMaxTime });
        assertNotEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(second));
        assertTrue( first.compareTo(second) > 0 );
        assertTrue( second.compareTo(first) < 0 );
        second.setDatasetMaxTimes(new Long[] { firstMaxTime, secondMaxTime });
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        // Ignores negligible differences in floating point values
        first.setMinDistance(minDist + 5.0E-7);
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        first.setLatsAtMin(new Double[] {firstLat, secondLat + 5.0E-7});
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));

        // And modulo on longitudes
        first.setLonsAtMin(new Double[] {firstLon, secondLon + 360.0});
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.equals(second));
        assertEquals(0, first.compareTo(second));
    }

}
