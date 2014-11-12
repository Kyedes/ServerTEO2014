import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;

public class SaveNote extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();

	public String execute(SaveNoteObject saveNoteObject) throws SQLException{
		String answer = "";

		String [] valueCalendar = {"calendarID"};
		String [] valueEvent = {"eventID"};
		String [] valueUser = {"userID"};

		String calendarID = queryBuilder.selectFrom(valueCalendar, "Events").where("eventName", "=", saveNoteObject.getEventName()).ExecuteQuery().toString();

		String userID = queryBuilder.selectFrom(valueUser, "Users").where("email", "=", saveNoteObject.getUserEmail()).ExecuteQuery().getString("userID");

		boolean author = false;

		resultSet = queryBuilder.selectFrom("Author").where("calendarID", "=", calendarID).ExecuteQuery();

		while(resultSet.next()){
			if(resultSet.getString("userID").equals(userID)){
				author = true;
			}	
		}
		
		if (author){
			String eventID = queryBuilder.selectFrom(valueEvent, "Events").where("eventName", "=", saveNoteObject.getEventName()).ExecuteQuery().getString("eventID");
			resultSet = queryBuilder.selectFrom("Notes").where("eventID", "=", eventID).ExecuteQuery();
			
			boolean noteExists = false;
			
			while(resultSet.next()){
				if(resultSet.getString("eventID").equalsIgnoreCase(eventID)){
					noteExists = true;
				}else{
					noteExists = false;
				}
			}
			if(noteExists){
				//Update
				//else
				//InsertInto
			}
			
		}

		return answer;
	}
}