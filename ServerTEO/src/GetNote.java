import model.Model;
import model.QueryBuild.QueryBuilder;


public class GetNote extends Model {

	QueryBuilder qb = new QueryBuilder();
	
	public String execute(GetNoteObject getNoteObject) throws Exception{
		String answer = "";
		
		resultSet = qb.selectFrom("Notes").where("eventID", "=", getNoteObject.getEventID()).ExecuteQuery();
		
		while(resultSet.next()){
			answer = resultSet.getString("note");
		}
		
		return answer;
	}

}
