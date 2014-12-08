import java.sql.ResultSet;
import java.sql.SQLException;

import shared.CreateEventObject;
import shared.CreateEventReturnObject;

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
	private CreateEventReturnObject cero = new CreateEventReturnObject();
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
		
		resultSet = qb.selectFrom("calendars").where("calendarname", "=", ceo.getCalendarName()).ExecuteQuery();
		
		
		
		if (resultSet.next()){
			String calendarID = resultSet.getString("calendarid");
			
			String[] fields = {
					"calendarid",
					"type", 
					"eventname", 
					"description", 
					"start", 
					"end", 
					"location",
			};
			
			String[] values = {
					calendarID,
					ceo.getType(),
					ceo.getEventName(),
					ceo.getDescription(), 
					String.format("%s %s", ceo.getStartDate(),ceo.getStartTime()),
					String.format("%s %s", ceo.getEndDate(),ceo.getEndTime()),
					ceo.getLocation()
			};
			
			qb.insertInto("events", fields).values(values).Execute();//use the boolean???
			cero.setCreated(true);
			cero.setMessage("the event " + ceo.getEventName() + " has been created" );
			
		}else{
			cero.setCreated(false);
			cero.setMessage("The associated calendar does not exist");
		}
		
		answer = gson.toJson(cero);
		
		return answer;
	}

}
