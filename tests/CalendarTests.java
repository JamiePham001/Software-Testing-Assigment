package tests;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.github.stefanbirkner.systemlambda.SystemLambda.SystemInStub;

import au.edu.sccs.csp3105.NBookingPlanner.Calendar;
import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;
import au.edu.sccs.csp3105.NBookingPlanner.Meeting;
import au.edu.sccs.csp3105.NBookingPlanner.Planner;

class CalendarTests {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream(); // data can be written to this byte array
	private final PrintStream originalOut = System.out; // write output data in text instead of bytes

	Calendar calendar;
	Meeting meetingMock;
	Meeting meetingMock2;
	
	// set up our stream to capture the console output
	@BeforeEach
	public void setStreams() {
	    System.setOut(new PrintStream(out));  // reassigns the output stream, we can store in the out variable
	}
	
	@BeforeEach
	public void setup() {
		meetingMock = Mockito.mock(Meeting.class);
		meetingMock2 = Mockito.mock(Meeting.class);
		calendar = new Calendar();
	};

	// reset after the test is done
	@AfterEach
	public void restoreInitialStreams() {
	    System.setOut(originalOut); // reset
	}
	
	@Test 
	public void isBusyFalse() throws ConflictsException {
		
		calendar = new Calendar();
		
		int month = 1;
		int day = 1;
		int start = 1;
		int end = 2;
		
		boolean e = calendar.isBusy(month, day, start, end);
		
		assertEquals(false, e);
		
	}
	
	@Test 
	public void isBusyTrue() throws ConflictsException {
		
		calendar = new Calendar();
		
		int month = 1;
		int day = 1;
		int start = 3;
		int end = 6;
		
		boolean e = calendar.isBusy(month, day, start, end);
		
		assertEquals(false, e);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTm 1")
	public void checkTimesMonthValidLower () throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(1,1,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	// error 1, month set too low
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTm 12")
	public void checkTimesMonthValidUpper () throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(11,1,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@Test 
	@DisplayName("CTm 0")
	public void checkTimesMonthInvalidLower() throws ConflictsException {
		
		int month = 0;
		int day = 1;
		int start = 1;
		int end = 2;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Month does not exist.", e.getMessage());
		}
		
	}

	@Test 
	@DisplayName("CTm 13")
	public void checkTimesMonthInvalidUpper() throws ConflictsException {
		
		int month = 13;
		int day = 1;
		int start = 1;
		int end = 2;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Month does not exist.", e.getMessage());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTd 1")
	public void checkTimesDayValidLower() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
		
	}
	
	// error 2. day set too low
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTd 31")
	public void checkTimesDayValidUpper() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(3,30,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
		
	}
	
	@Test 
	@DisplayName("CTd 0")
	public void checkTimesDayInvalidLower() throws ConflictsException {

		int month = 6;
		int day = 0;
		int start = 1;
		int end = 2;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Day does not exist.", e.getMessage());
		}
		
	}
	
	@Test 
	@DisplayName("CTd 32")
	public void checkTimesDayInvalidUpper() throws ConflictsException {

		int month = 6;
		int day = 31;
		int start = 1;
		int end = 2;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Day does not exist.", e.getMessage());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTs 0")
	public void checkTimesStartValidLower() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,0,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTs 23")
	public void checkTimesStartValidUpper() throws ConflictsException {

		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,22,23);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@Test 
	@DisplayName("CTs -1")
	public void checkTimesStartInvalidLower() throws ConflictsException {

		int month = 6;
		int day = 1;
		int start = -1;
		int end = 2;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}
	
	@Test 
	@DisplayName("CTs 24")
	public void checkTimesStartInvalidUpper() throws ConflictsException {

		int month = 6;
		int day = 1;
		int start = 24;
		int end = 0;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTe 0")
	public void checkTimesEndValidLower() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,0,0);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTe 23")
	public void checkTimesEndValidUpper() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,0,23);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@Test 
	@DisplayName("CTe -1")
	public void checkTimesEndtInvalidLower() throws ConflictsException {

		int month = 6;
		int day = 1;
		int start = 0;
		int end = -1;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}
	
	@Test 
	@DisplayName("CTe 24")
	public void checkTimesEndtInvalidUpper() throws ConflictsException {

		int month = 6;
		int day = 1;
		int start = 0;
		int end = 24;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}
	
	@Test 
	@DisplayName("CTSoE")
	public void checkTimesStartOverEnd() throws ConflictsException {

		int month = 6;
		int day = 1;
		int start = 7;
		int end = 5;
		
		try {
			Calendar.checkTimes(month, day, start, end);
			
			fail("Expected exception was not thrown.");
		} catch (Exception e) {
			assertEquals("Meeting starts before it ends.", e.getMessage());
		}
	}
	
	@Test
	void addMeetingConflict() throws ConflictsException {
		int month = 3;
		int day = 12;
		int start = 12;
		int end = 13;
		String desc = "meeting desc";
		
		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);
		when(meetingMock.getStartTime()).thenReturn(start);
		when(meetingMock.getEndTime()).thenReturn(end);
		when(meetingMock.getDescription()).thenReturn(desc);
		
		when(meetingMock2.getMonth()).thenReturn(month);
		when(meetingMock2.getDay()).thenReturn(day);
		when(meetingMock2.getStartTime()).thenReturn(start);
		when(meetingMock2.getEndTime()).thenReturn(end);
		when(meetingMock2.getDescription()).thenReturn(desc);
		
		calendar.addMeeting(meetingMock);
		
		Assertions.assertThrows(ConflictsException.class, () -> {
			calendar.addMeeting(meetingMock2);
		});
	}
	@Test
	void addMeetingPass() throws ConflictsException {
		// variables to set the meeting details
		int month = 3;
		int day = 12;
		int start = 12;
		int end = 13;
		
		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);
		when(meetingMock.getStartTime()).thenReturn(start);
		when(meetingMock.getEndTime()).thenReturn(end);
		
		calendar.addMeeting(meetingMock);
		
		assertEquals(true, calendar.isBusy(month,day,start,end));
			
	}
	
	@Test
	void clearSchedulePass() throws ConflictsException {
		int month = 3;
		int day = 12;
		
		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);

		calendar.addMeeting(meetingMock);
		calendar.clearSchedule(month, day);
		
	    
	    // Verify that the day's meeting list is empty
		assertEquals(false, calendar.isBusy(month,day,0, 23));
	}
	
	@Test
	void printAgendaEmpty()throws ConflictsException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		int month = 5;
		
	    // Use reflection to access the private 'occupied' object
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    @SuppressWarnings("unchecked")
		ArrayList<ArrayList<ArrayList<Meeting>>> occupiedValue = (ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);

	    // Modify the 'occupied' object to have an empty list for the specified month
	    occupiedValue.set(month, new ArrayList<>());

	    String agenda = calendar.printAgenda(month);

	    assertEquals("No Meetings booked for this month.\n\n", agenda);
	}
	
	@Test
	void printAgendaExists()throws ConflictsException{
		int month = 5;
		
	    String agenda = calendar.printAgenda(month);

	    assertEquals("Agenda for "+month+":\n", agenda);
	}
	
	@Test
	void printAgendaDayMonthEmpty(){
		int month = 5;
		int day = 8;

	    String agenda = calendar.printAgenda(month, day);

	    assertEquals("No Meetings booked on this date.\n\n", agenda);
	}
	
	@Test
	void printAgendaPresent() throws ConflictsException{
		int month = 3;
		int day = 15;
		int start = 12;
		int end = 13;

		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);
		when(meetingMock.getStartTime()).thenReturn(start);
		when(meetingMock.getEndTime()).thenReturn(end);
		
		calendar.addMeeting(meetingMock);

	    String expectedAgenda = "Agenda for "+month+"/"+day+" are as follows:\n"+meetingMock+"\n";
	    String agenda = calendar.printAgenda(month, day);

	    assertEquals(expectedAgenda, agenda);
	}
	
	@Test
	void getMeetingTest() throws ConflictsException{
		int month = 3;
		int day = 15;

		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);
		
		calendar.addMeeting(meetingMock);
		calendar.getMeeting(month, day, 0);

		assertEquals(true, calendar.isBusy(month,day,0, 23));
	}
	
	@Test
	void removeMeetingTest() throws ConflictsException{
		int month = 3;
		int day = 15;

		when(meetingMock.getMonth()).thenReturn(month);
		when(meetingMock.getDay()).thenReturn(day);
		
		calendar.addMeeting(meetingMock);
		calendar.removeMeeting(month, day, 0);

		assertEquals(false, calendar.isBusy(month,day,0, 23));
	}
}