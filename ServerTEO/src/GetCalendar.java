import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import shared.Event;
import shared.GetCalendarObject;
import shared.GetCalendarReturnObject;
import model.QueryBuild.*;

import com.google.gson.*;

public class GetCalendar{
	
	private QueryBuilder qBuilder = new QueryBuilder();
	private ImportCalendarData icd = new ImportCalendarData();
	private ResultSet resultSet;
	private ArrayList<Event> calendar;
	private ArrayList<ArrayList<Event>> calendars = new ArrayList<ArrayList<Event>>();
	private Event event;
	private ArrayList<String> calid;
	
	
	private Gson gson = new GsonBuilder().create();
	private GetCalendarReturnObject gcro = new GetCalendarReturnObject();
	
	public String execute (GetCalendarObject gcObject) throws SQLException{
		
		String answer = "";
		
		icd.importCalendar(gcObject.getUserName());
		
		resultSet = qBuilder.selectFrom("users").where("username", "=", gcObject.getUserName()).ExecuteQuery();
		resultSet.next();
		
		String userID = resultSet.getString("userid");
		
		resultSet = qBuilder.selectFrom(new String [] {"calendarID"}, "Subscription").where("userID", "=", userID).ExecuteQuery();
		
		calid = new ArrayList<String>();
		
		while (resultSet.next()){
			
			calid.add(resultSet.getString("calendarid"));
		}
		for (String i : calid){
			
			resultSet = qBuilder.selectFrom("Events").where("calendarID", "=", i).ExecuteQuery();
			
			calendar = new ArrayList<Event>();
			
			while (resultSet.next()){
				event = new Event();
				
				event.setActivityid(resultSet.getString("calendarid"));
				event.setEventid(resultSet.getString("eventid"));
				event.setType(resultSet.getString("type"));
				event.setTitle(resultSet.getString("eventname"));
				event.setDescription(resultSet.getString("description"));
				
				Date startDate = resultSet.getDate("start");
				Time startTime = resultSet.getTime("start");
				
				Date endDate = resultSet.getDate("end");
				Time endTime = resultSet.getTime("end");
				
				event.setLocation(resultSet.getString("location"));
								
				String stringStartDate = String.valueOf(startDate);
				String stringStartTime = String.valueOf(startTime);				
				String stringEndDate = String.valueOf(endDate);
				String stringEndTime = String.valueOf(endTime);
				
				ArrayList<String> alStart = new ArrayList<String>();
				alStart.add(stringStartDate + " " + stringStartTime);
				
				ArrayList<String> alEnd = new ArrayList<String>();
				alEnd.add(stringEndDate + " " + stringEndTime);
				
				event.setStart(alStart);
				event.setEnd(alEnd);
								
				calendar.add(event);
				
			}
			
			calendars.add(calendar);
			
		}
		
		gcro.setCalendars(calendars);
		
		
		//Test to see if calendars are being made
//		for (ArrayList<Event> x : calendars){
//			System.out.print(x.size() + "\n");
//		}
		
		answer = gson.toJson(gcro);
		
		return answer;
		
	}
}
