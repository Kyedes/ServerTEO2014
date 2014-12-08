import java.sql.ResultSet;
import java.sql.SQLException;

import shared.SaveNoteObject;
import shared.SaveNoteReturnObject;

import com.google.gson.Gson;

import model.QueryBuild.QueryBuilder;

public class SaveNote{
	private QueryBuilder queryBuilder = new QueryBuilder();
	private SaveNoteReturnObject snro = new SaveNoteReturnObject();
	private Gson gson = new Gson();
	private ResultSet resultSet;

	public String execute(SaveNoteObject saveNoteObject) throws SQLException{
		String answer = "";

		boolean author = false;

		resultSet = queryBuilder.selectFrom("Events").where("eventName", "=", saveNoteObject.getEventName()).ExecuteQuery();
		if(resultSet.next()){
			String calendarID = resultSet.getString("calendarid");
			
			resultSet = queryBuilder.selectFrom("Users").where("username", "=", saveNoteObject.getUserEmail()).ExecuteQuery();
			if (resultSet.next()){
				String userID = resultSet.getString("userid");
				
				resultSet = queryBuilder.selectFrom("autherrights").where("calendarID", "=", calendarID).ExecuteQuery();

				while(resultSet.next()){
					if(resultSet.getString("userID").equals(userID)){
						author = true;
					}
				}
				if (author){
					resultSet = queryBuilder.selectFrom("Events").where("eventName", "=", saveNoteObject.getEventName()).ExecuteQuery();
					resultSet.next();
					String eventID = resultSet.getString("eventID");
					
					resultSet = queryBuilder.selectFrom("Notes").where("eventID", "=", eventID).ExecuteQuery();
					
					if(resultSet.next()){
						queryBuilder.update("notes", new String [] {"notecontent"}, new String [] {saveNoteObject.getNoteContent()}).where("eventid", "=", eventID).Execute();
						snro.setMessage("Note updated");
						snro.setUpdated(true);
					}else{
						queryBuilder.insertInto("notes", new String [] {"notecontent"}).values( new String [] {saveNoteObject.getNoteContent()}).Execute();
						snro.setMessage("Note updated");
						snro.setUpdated(true);
					}
				}else{
					snro.setUpdated(false);
					snro.setMessage("you do not have the rights to edit this event");
				}
			}else{
				snro.setUpdated(false);
				snro.setMessage("user does not exist");
			}
			
		}else{
			snro.setUpdated(false);
			snro.setMessage("event does not exist");
		}
		
		answer = gson.toJson(snro);

		return answer;
	}
}