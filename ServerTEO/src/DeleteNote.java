import java.sql.SQLException;

import com.google.gson.Gson;

import model.QueryBuild.QueryBuilder;

import java.sql.ResultSet;

import shared.DeleteNoteObject;
import shared.DeleteNoteReturnObject;

public class DeleteNote {
	private boolean deleted;
	private String message;
	private QueryBuilder qb = new QueryBuilder();
	private Gson gson = new Gson();
	private DeleteNoteReturnObject dnrt = new DeleteNoteReturnObject();
	private ResultSet resultSet;
	private String answer;
	
	public String execute(DeleteNoteObject deleteNoteObject) throws SQLException{
		resultSet = qb.selectFrom("events").where("eventid", "=", deleteNoteObject.getEventID()).ExecuteQuery();
		resultSet.next();
		String eventid = resultSet.getString("eventid");
		
		resultSet = qb.selectFrom("notes").where("eventID", "=", eventid).ExecuteQuery();
		if(resultSet.next()){
			qb.deleteFrom("Notes").where("eventid", "=", eventid).Execute();
			deleted = true;
			message = "the note has been deleted";
		}else{
			deleted = false;
			message = "The note does not exist";
		}
		
		dnrt.setDeleted(deleted);
		dnrt.setMessage(message);
		
		answer = gson.toJson(dnrt);
		
		return answer;
	}

}
