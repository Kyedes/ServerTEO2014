import java.sql.SQLException;

import shared.GetNoteObject;
import shared.GetNoteReturnObject;

import com.google.gson.Gson;

import model.Model;
import model.QueryBuild.QueryBuilder;

public class GetNote extends Model {

	private QueryBuilder qb = new QueryBuilder();
	private String message;
	private String answer;
	private GetNoteReturnObject gnro = new GetNoteReturnObject();
	private Gson gson = new Gson();
		
	public String execute(GetNoteObject getNoteObject) throws SQLException{
		resultSet = qb.selectFrom("Notes").where("eventID", "=", getNoteObject.getEventID()).ExecuteQuery();
		
		if(resultSet.next()){
			message = resultSet.getString("notecontent");
		}else{
			message = "this event does not have a note, yet.";
		}
		gnro.setMessage(message);
		answer = gson.toJson(gnro);
		
		return answer;
	}
}
