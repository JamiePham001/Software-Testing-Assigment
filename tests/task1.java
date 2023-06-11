package tests;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import au.edu.sccs.csp3105.NBookingPlanner.Calendar;
import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;
import au.edu.sccs.csp3105.NBookingPlanner.Meeting;
import au.edu.sccs.csp3105.NBookingPlanner.Planner;
import au.edu.sccs.csp3105.NBookingPlanner.Room;

// A small tutorial testing class
public class task1 {

	// save out the console output to a stream, rather than printing to the actual console window
	private final ByteArrayOutputStream out = new ByteArrayOutputStream(); // data can be written to this byte array
	private final PrintStream originalOut = System.out; // write output data in text instead of bytes
	
	Calendar calendar;
	Room room;
	
	// lets make the planner class, so we can use it to test
	private Planner planner;

	// helper method, takes in the console stream, cleans up the text and returns the last line
	private String GetLastConsoleOutput(String input) {
		String output = input;
		output = output.strip();
		String[] lines = output.split("\n"); 
		String lastLine = lines[lines.length - 1];
		return lastLine;
	}
	
	// set up our stream to capture the console output
	@BeforeEach
	public void setStreams() {
	    System.setOut(new PrintStream(out));  // reassigns the output stream, we can store in the out variable
	}
	
	@BeforeEach
	public void setup() {
		calendar = new Calendar();
		room = new Room();
	};

	// reset after the test is done
	@AfterEach
	public void restoreInitialStreams() {
	    System.setOut(originalOut); // reset
	}
		
	@Test
	@DisplayName("Leap Year 1")
	void LeapYear1() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 2;
		int day = 29;
		int start = 9;
		int end = 10;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
							Integer.toString(day),
							Integer.toString(start),
							Integer.toString(end),
							roomIn, 
							personIn, 
							complete, 
							description,
							"cancel").execute(() -> {
			//call the schedule meeting method	
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals(true, calendar.isBusy(month, day, start, end));		
	}
	
	@Test
	@DisplayName("11:59")
	void midnight() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 11;
		int day = 29;
		int start = 23;
		int end = 0;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
							Integer.toString(day),
							Integer.toString(start),
							Integer.toString(end),
							roomIn, 
							personIn, 
							complete, 
							description,
							"cancel").execute(() -> {
			//call the schedule meeting method	
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Illegal hour.", GetLastConsoleOutput(out.toString()));		
	}
	@Test
	@DisplayName("30th November")
	void November() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 11;
		int day = 30;
		int start = 9;
		int end = 10;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
							Integer.toString(day),
							Integer.toString(start),
							Integer.toString(end),
							roomIn, 
							personIn, 
							complete, 
							description
							).execute(() -> {
			//call the schedule meeting method	
			planner.scheduleMeeting();
        });
		withTextFromSystemIn(Integer.toString(month),
                Integer.toString(day),
                personIn

                ).execute(() -> {
            //call the schedule meeting method

            planner.checkAgendaPerson();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Attending: Mark Colin", GetLastConsoleOutput(out.toString()));	
	}
	
	@Test
    @DisplayName("Book 32nd")
    void Booking32nd() throws Exception {
        // make spy, we can use some of the real methods and mock some of the other methods
        planner = Mockito.spy(Planner.class);

        //override main menu with do nothing..we are mocking this ha-ah!
        Mockito.doNothing().when(planner).mainMenu();

        //set inputs as per table
        int startmonth = 6;
        int startday = 32;
        int endmonth = 8;
        int endday = 6;
        String personIn = "Mark Colin";
        String all = "all";


        //buffer input values, console will call these one by one! Thanks system lambda library
        withTextFromSystemIn(Integer.toString(startmonth),
                Integer.toString(startday),
                Integer.toString(endmonth),
                Integer.toString(endday),
                personIn,
                "cancel").execute(() -> {
            //call the schedule meeting method
            planner.scheduleVacation();

        });
		withTextFromSystemIn(Integer.toString(startmonth),
                Integer.toString(startday),
                personIn

                ).execute(() -> {
            //call the schedule meeting method

            planner.checkAgendaPerson();
        });

        Assertions.assertEquals("Attending: Mark Colin", GetLastConsoleOutput(out.toString()));
    }
}
