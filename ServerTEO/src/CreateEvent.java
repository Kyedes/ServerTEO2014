import java.sql.ResultSet;
import java.sql.SQLException;

import Shared.CreateEventObject;
import Shared.CreateEventReturnObject;

import com.google.gson.Gson;

import model.QueryBuild.QueryBuilder;

/**
 * The class method
 * @author Esben
 *
 */

public class CreateEvent {
	private QueryBuilder qb = new QueryBuilder();
	private ResultSet resultSet;
	private CreateEventReturnObject cero;
	private Gson gson = new Gson();

	/**
	 * The execute method uses the QueryBuilder to create an event, of the provided information, in the database provided that a event with the same name does not already exist in the same calendar.
	 * it returns the Json string of the return object telling whether the event has been created; and if not, why.
	 * @param ceo
	 * @return
	 * @throws SQLException
	 */
	public String execute(CreateEventObject ceo) throws SQLException{
		String answer = "";
		
		resultSet = qb.selectFrom(new String [] {"calendarname"},"calendars").where("calendarname", "=", ceo.getCalendarName()).ExecuteQuery();
		
		String[] fields = {
				"eventName", 
				"description", 
				"location", 
				"createdBy", 
				"calendarname", 
				"startDate", 
				"startTime", 
				"endDate", 
				"endTime", 
		};
		
		String[] values = {
				ceo.getEventName(),
				ceo.getDescription(),
				ceo.getLocation(), 
				ceo.getCreatedby(), 
				ceo.getCalendarName(), 
				ceo.getStartDate(),
				ceo.getStartTime(), 
				ceo.getEndDate(),
				ceo.getEndTime()
		};
		
		if (resultSet.next()){
			
			qb.insertInto("events", fields).values(values).Execute();//use the boolean???
			
			cero.setCreated(true);
			cero.setMessage("the event " + ceo.getEventName() + " has been created" );
			
		}else{
			cero.setCreated(false);
			cero.setMessage("The associated calendar does not exist");
		}
		
		answer = (String) gson.toJson(cero);
		
		return answer;
	}

}
