import java.sql.SQLException;

import shared.DeleteEventObject;
import shared.DeleteEventReturnObject;

import com.google.gson.Gson;

import model.Model;
import model.QueryBuild.QueryBuilder;


public class DeleteEvent extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	private boolean author;
	private String answer = "";
	private String message;
	private boolean deleted = false;
	private Gson gson = new Gson();
	private DeleteEventReturnObject dero = new DeleteEventReturnObject();

	public String execute(DeleteEventObject deleteEventObject) throws SQLException{

		resultSet = queryBuilder.selectFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();

		if(resultSet.next()){

			resultSet = queryBuilder.selectFrom("Events").where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();
			resultSet.next();
			String calendarID = resultSet.getString("calendarid");

			resultSet = queryBuilder.selectFrom("Users").where("username", "=", deleteEventObject.getuserID()).ExecuteQuery();
			resultSet.next();
			String userID = resultSet.getString("userID");


			resultSet = queryBuilder.selectFrom("autherrights").where("calendarID", "=", calendarID).ExecuteQuery();

			author = false;

			while(resultSet.next()){
				if(resultSet.getString("userID").equals(userID)){
					author = true;
				}
			}

			if (author){


				resultSet = queryBuilder.selectFrom("Events").where("eventName", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();
				resultSet.next();
				String eventID = resultSet.toString();

				queryBuilder.deleteFrom("Notes").where("eventID", "=", eventID).Execute();

				queryBuilder.deleteFrom(dbConfig.getEvents()).where("eventName", "=", deleteEventObject.getEventToDelete()).Execute();

				message = String.format("Event " + deleteEventObject.getEventToDelete() + " has been deleted, with associated note.");
				deleted = true;

			}else{
				deleted = false;
				message = "Your are not the owner of this Event";
			}
		}else{
			deleted = false;
			message = "Event does not exist";
		}

		dero.setMessage(message);
		dero.setDeleted(deleted);

		answer = gson.toJson(dero);
		return answer;
	}
}
