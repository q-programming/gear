package com.qprogramming.activtytracker;

import com.qprogramming.activtytracker.dto.Activity;
import com.qprogramming.activtytracker.dto.Type;
import com.qprogramming.activtytracker.exceptions.ConfigurationException;
import com.qprogramming.activtytracker.report.dto.Range;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.qprogramming.activtytracker.ActivityService.DATABASE_FILE;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {

    private Properties propertiesMock;
    private ActivityService activityService;

    @Before
    public void setUp() {
        propertiesMock = mock(Properties.class);
        URL resource = getClass().getResource("database");
        when(propertiesMock.getProperty(DATABASE_FILE)).thenReturn(resource.getFile());
        when(propertiesMock.getOrDefault(anyString(), anyLong())).then(returnsSecondArg());
        activityService = new ActivityService(propertiesMock);
    }


    @Test
    public void testLoadLastActive() {
        Activity ac1 = new Activity();
        ac1.setStart(ZonedDateTime.now());
        ac1.setEnd(ZonedDateTime.now().plusHours(1));
        Activity ac2 = new Activity();
        ac2.setStart(ZonedDateTime.now().plusHours(1));
        List<Activity> activities = Arrays.asList(ac1, ac2);
        Activity lastActive = activityService.getLastActive(activities);
        assertNotNull(lastActive);
    }

    @Test
    public void testLoadAll() throws IOException, ConfigurationException {
        List<Activity> activities = activityService.loadAll();
        assertTrue(activities.size() > 1);
    }

    @Test(expected = ConfigurationException.class)
    public void testLoadAllException() throws IOException, ConfigurationException {
        when(propertiesMock.getProperty(DATABASE_FILE)).thenReturn(null);
        List<Activity> activities = activityService.loadAll();
        fail("Exception was not thrown");
    }


    @Test
    public void testLoadLastActiveNotFound() {
        Activity ac1 = new Activity();
        ac1.setStart(ZonedDateTime.now());
        ac1.setEnd(ZonedDateTime.now().plusHours(1));
        Activity ac2 = new Activity();
        ac2.setStart(ZonedDateTime.now().plusHours(1));
        ac2.setEnd(ZonedDateTime.now().plusHours(2));
        List<Activity> activities = Arrays.asList(ac1, ac2);
        Activity lastActive = activityService.getLastActive(activities);
        assertNull(lastActive);
    }

    @Test
    public void testLoadLastActiveEmptyList() {
        List<Activity> activities = new ArrayList<>();
        Activity lastActive = activityService.getLastActive(activities);
        assertNull(lastActive);
    }


    @Test
    public void testAddNewActivityComplete() throws IOException, ConfigurationException {
        Activity ac = new Activity();
        ac.setType(Type.SM);
        ac.setStart(ZonedDateTime.now());
        Activity activity = activityService.addNewActivity(ac, true);
        assertNotNull(activity.getStartTime());
    }

    @Test
    public void testLoadInrange() throws IOException, ConfigurationException {
        Range range = new Range();
        range.setFromDate(LocalDate.now().minusDays(2));
        range.setToDate(LocalDate.now().minusDays(1));
        List<Activity> activities = activityService.loadAllInRange(range);
        assertTrue("All activities in test resource file should be older than today - 1", activities.isEmpty());
    }

}

