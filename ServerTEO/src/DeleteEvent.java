import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;


public class DeleteEvent extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	private boolean auther;

	public String execute(DeleteEventObject deleteEventObject) throws SQLException{
		String answer = "";

		String[] valuesCalendar = {"calnedarID"};
		String[] valuesUser = {"userID"};

		String calendarID = queryBuilder.selectFrom(valuesCalendar, "Events").where("evntName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery().toString();

		int userID = queryBuilder.selectFrom(valuesUser, "Users").where("email", "=", deleteEventObject.getAuthEvent()).ExecuteQuery().getInt("userID");

		resultSet = queryBuilder.selectFrom("CalendarUser").where("calendarID", "=", calendarID).ExecuteQuery();

		auther = false;

		while(resultSet.next()){
			if(resultSet.getInt("userID") == (userID)){
				auther = true;
			}
		}

		if (auther){


			resultSet = queryBuilder.selectFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();

			while(resultSet.next()){

				queryBuilder.deleteFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete());
				String[] valuesEvent = {"eventID"};
				String eventID = queryBuilder.selectFrom(valuesEvent, "Events").where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery().toString();
				queryBuilder.deleteFrom("Notes").where("eventID", "=", eventID);
				answer = String.format("Event " + deleteEventObject.getEventToDelete() + "has been deleted, with associated note.");

			}
		}else{
			answer = "Your are not the owner of this Event";
		}

		return answer;
	}
}
