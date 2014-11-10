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

		int userID = queryBuilder.selectFrom(valueUser, "Users").where("email", "=", saveNoteObject.getUserEmail()).ExecuteQuery().getInt(1);

		boolean author = false;

		resultSet = queryBuilder.selectFrom("Author").where("calendarID", "=", calendarID).ExecuteQuery();

		while(resultSet.next()){
			if(resultSet.getInt("userID") == userID){
				author = true;
			}	
		}
		
		if (author){
			int eventID = queryBuilder.selectFrom(valueEvent, "Events").where("eventName", "=", saveNoteObject.getEventName()).ExecuteQuery().getInt("eventID");
			resultSet = queryBuilder.selectFrom("Notes").where("eventID", "=", eventID);
			
			boolean noteExists;
			
			while(resultSet.next()){
				if(resultSet.getInt("eventID") == eventID){
					noteExists = true;
				}	
			}
			if(noteExists){
				//Update
				//else
				//InsertInto
			}
			
		}


	}
}