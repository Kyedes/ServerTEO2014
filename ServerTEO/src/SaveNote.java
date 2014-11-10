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
		String [] valueNote = {"noteID"};
		String [] valueUser = {"userID"};
		
		String noteID = queryBuilder.selectFrom(valueNote, "notes").where("NoteName", "=", saveNoteObject.getNoteEvent()).ExecuteQuery().toString();
		
		int userID = queryBuilder.selectFrom(valueUser, "Users").where("UserName", "=", saveNoteObject.getNoteEvent()).ExecuteQuery().getInt(1);
	}

}
