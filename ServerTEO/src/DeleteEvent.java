import java.sql.SQLException;

import Shared.DeleteEventObject;
import Shared.DeleteEventReturnObject;

import com.google.gson.Gson;

import model.Model;
import model.QueryBuild.QueryBuilder;


public class DeleteEvent extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	private boolean auther;
	private String answer = "";
	private String message;
	private boolean deleted = false;
	private Gson gson = new Gson();
	private DeleteEventReturnObject dero = new DeleteEventReturnObject();

	public String execute(DeleteEventObject deleteEventObject) throws SQLException{
		
		String[] valuesCalendar = {"calnedarID"};
		String[] valuesUser = {"userID"};

		String calendarID = queryBuilder.selectFrom(valuesCalendar, "Events").where("evntName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery().toString();

		String userID = queryBuilder.selectFrom(valuesUser, "Users").where("email", "=", deleteEventObject.getuserID()).ExecuteQuery().getString("userID");


		resultSet = queryBuilder.selectFrom("autherrights").where("calendarID", "=", calendarID).ExecuteQuery();

		auther = false;

		while(resultSet.next()){
			if(resultSet.getString("userID").equals(userID)){
				auther = true;
			}
		}

		if (auther){
			resultSet = queryBuilder.selectFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();

			if(resultSet.next()){

				queryBuilder.deleteFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete());
				String[] valuesEvent = {"eventID"};
				String eventID = queryBuilder.selectFrom(valuesEvent, "Events").where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery().toString();
				queryBuilder.deleteFrom("Notes").where("eventID", "=", eventID);
				message = String.format("Event " + deleteEventObject.getEventToDelete() + "has been deleted, with associated note.");
				deleted = true;
			}else{
				deleted = false;
				message = "event does not exist";
			}
		}else{
			deleted = false;
			message = "Your are not the owner of this Event";
		}

		dero.setMessage(message);
		dero.setDeleted(deleted);
		
		answer = gson.toJson(dero);
		return answer;
	}
}
