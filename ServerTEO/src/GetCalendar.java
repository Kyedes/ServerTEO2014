import model.Model;
import model.QueryBuild.*;
public class GetCalendar extends Model{
	
	private QueryBuilder qBuilder = new QueryBuilder();
	
	public String execute (String UserID) throws Exception{
		
		String returnString = "";
		
		//Her skal alle kalendre som brugeren abonere p� hentes fra databasen
		resultSet = qBuilder.selectFrom("calendar").where("User", "=", UserID).ExecuteQuery();
		
		//her skal alle kalendrene l�gges i en array som kan castes til en jsonstring og sendes
		
		return returnString;
		
	}
}
