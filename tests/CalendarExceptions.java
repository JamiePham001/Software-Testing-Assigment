package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import au.edu.sccs.csp3105.NBookingPlanner.Calendar;
import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;
import au.edu.sccs.csp3105.NBookingPlanner.Meeting;

class CalendarExceptions {

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
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTm 1")
	public void checkTimesMonthValidLower () throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(1,1,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTm 6")
	public void checkTimesMonthValidMid () throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(1,1,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
	}
	
	// error 1, month set too low
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTm 11")
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
	@DisplayName("CTm 12")
	public void checkTimesMonthInvalidUpper() throws ConflictsException {
		
		int month = 12;
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
	
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTd 15")
	public void checkTimesDayValidMid() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,15,1,2);
		
		verifyZeroInteractions(conflictsExceptionMock);
		
	}
	
	// error 2. day set too low
	@SuppressWarnings("deprecation")
	@Test 
	@DisplayName("CTd 30")
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
	@DisplayName("CTd 31")
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
	@DisplayName("CTs 12")
	public void checkTimesStartValidMid() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,0,12);
		
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
	@DisplayName("CTe 12")
	public void checkTimesEndValidMid() throws ConflictsException {
		
		ConflictsException conflictsExceptionMock = mock(ConflictsException.class);

		Calendar.checkTimes(6,1,0,12);
		
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
	
}
