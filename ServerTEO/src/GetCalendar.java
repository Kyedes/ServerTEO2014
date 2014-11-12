import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import model.Model;
import model.QueryBuild.*;

import com.google.gson.*;

public class GetCalendar{
	
	private QueryBuilder qBuilder = new QueryBuilder();
	private ResultSet resultSet2;
	private ResultSet resultSet;
	private ArrayList<ArrayList<Event>> calendars;
	private ArrayList<Event> calendar;
	private Gson gson = new GsonBuilder().create();
	
	
	public String execute (GetCalendarObject gcObject) throws SQLException{
		
		String answer = "";
		
		String [] valuesCalendars = {"calendarID"};
		
		resultSet = qBuilder.selectFrom(valuesCalendars, "Subsciption").where("userID", "=", gcObject.getUserID()).ExecuteQuery();
		
		while(resultSet.next()){
			resultSet2 = qBuilder.selectFrom("Events").where("calendarID", "=", resultSet.getString("calendarID")).ExecuteQuery();
			
			while (resultSet2.next()){
				
				int eventID = resultSet2.getInt("eventid");
				int type = resultSet2.getInt("type");
				int location = resultSet2.getInt("location");
				int createdby = resultSet2.getInt("createdby");
				
				
				Date startDate = resultSet2.getDate("start");
				Time startTime = resultSet2.getTime("start");
				
				Date endDate = resultSet2.getDate("end");
				Time endTime = resultSet2.getTime("end");
				
//				String nameEvent = resultSet2.getString("name");
//				String text = resultSet2.getString("text");
				
				String stringEventID = String.valueOf(eventID);
				String stringType = String.valueOf(type);
				String stringLocation = String.valueOf(location);
				String stringCreatedby = String.valueOf(createdby);
				String stringStartDate = String.valueOf(startDate);
				String stringStartTime = String.valueOf(startTime);				
				String stringEndDate = String.valueOf(endDate);
				String stringEndTime = String.valueOf(endTime);
				
				ArrayList<String> alStart = new ArrayList<String>();
				alStart.add(stringStartDate + "" + stringStartTime);
				
				ArrayList<String> alEnd = new ArrayList<String>();
				alEnd.add(stringEndDate + "" + stringEndTime);
				
				
				System.out.println(String.valueOf(startDate.getTime()));
				
				calendar.add(new Event(stringEventID, stringEventID, stringType, stringType, stringLocation, stringLocation,stringCreatedby, alStart, alEnd));
				
			}
			calendars.add(calendar);
			calendar.clear();
		}
		
		answer = gson.toJson(calendars);
			
		return answer;
		
	}
}
