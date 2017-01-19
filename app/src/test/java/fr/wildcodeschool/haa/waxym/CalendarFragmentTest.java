package fr.wildcodeschool.haa.waxym;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by tuffery on 19/01/17.
 */
public class CalendarFragmentTest {

    private CalendarFragment calendarFragment;

    @Before
    public void setUp() throws Exception {
        calendarFragment = new CalendarFragment();

    }

    @Test
    public void isNotWeekEnd() throws Exception {
        Calendar instance = Calendar.getInstance();
        assertFalse(calendarFragment.isWeekend(instance));

        instance.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        assertTrue(calendarFragment.isWeekend(instance));

        instance.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        assertTrue(calendarFragment.isWeekend(instance));

        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        assertFalse(calendarFragment.isWeekend(instance));
    }



}