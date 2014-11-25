import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import model.QueryBuild.*;
import Shared.Event;
import Shared.GetCalendarObject;
import Shared.GetCalendarReturnObject;

import com.google.gson.*;

public class GetCalendar{
	
	private QueryBuilder qBuilder = new QueryBuilder();
	private ImportCalendarData icd = new ImportCalendarData();
	private ResultSet resultSet2;
	private ResultSet resultSet;
	private ArrayList<ArrayList<Event>> calendars;
	private ArrayList<Event> calendar;
	
	private Gson gson = new GsonBuilder().create();
	private GetCalendarReturnObject gcro = new GetCalendarReturnObject();
	
	public String execute (GetCalendarObject gcObject) throws SQLException{
		
		String answer = "";
		
		icd.importCalendar(gcObject.getUserID());
		
		resultSet = qBuilder.selectFrom(new String [] {"calendarID"}, "Subscription").where("userID", "=", gcObject.getUserID()).ExecuteQuery();
		
		while (resultSet.next()){
			
			resultSet2 = qBuilder.selectFrom("Events").where("calendarID", "=", resultSet.getString("calendarID")).ExecuteQuery();
			
			while (resultSet2.next()){
				
				String calendarID = resultSet2.getString("calendarid");
				String eventID = resultSet2.getString("eventid");
				String type = resultSet2.getString("type");
				String eventName = resultSet2.getString("eventname");
				String description = resultSet2.getString("description");
				
				Date startDate = resultSet2.getDate("start");
				Time startTime = resultSet2.getTime("start");
				
				Date endDate = resultSet2.getDate("end");
				Time endTime = resultSet2.getTime("end");
				
				String location = resultSet2.getString("location");
				
				String stringStartDate = String.valueOf(startDate);
				String stringStartTime = String.valueOf(startTime);				
				String stringEndDate = String.valueOf(endDate);
				String stringEndTime = String.valueOf(endTime);
				
				ArrayList<String> alStart = new ArrayList<String>();
				alStart.add(stringStartDate + "" + stringStartTime);
				
				ArrayList<String> alEnd = new ArrayList<String>();
				alEnd.add(stringEndDate + "" + stringEndTime);
				
				
//				System.out.println(String.valueOf(startDate.getTime()));
				
				calendar.add(new Event(calendarID, eventID, type, eventName, description, alStart, alEnd, location));
				alStart.clear();
				alEnd.clear();
			}
			calendars.add(calendar);
			calendar.clear();
		}
		
		gcro.setCalendars(calendars);
		
		for (ArrayList<Event> i : calendars){
			System.out.print(i.size());
		}
		
		answer = gson.toJson(gcro);
			
		return answer;
		
	}
}
