import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;


public class DeleteEvent extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	
	public String execute(DeleteEventObject deleteEventObject) throws SQLException{
		String answer = "";
		
		resultSet = queryBuilder.selectFrom(dbConfig.getEvents()).where("Name", "=", deleteEventObject.getEventToDelete()).ExecuteQuery();
		
		while(resultSet.next()){
			String [] value = {"CreatedBy"};
			
			//TODO compare list of user/event with user and event name
			
			String eventAuthor = queryBuilder.selectFrom(value, dbConfig.getEvents()).where("Name", "=", deleteEventObject.getEventToDelete()).ExecuteQuery().toString();
			
			if(deleteEventObject.getAuthEvent().equalsIgnoreCase(eventAuthor)){
				
				queryBuilder.deleteFrom(dbConfig.getEvents()).where("Name", "=", deleteEventObject.getEventToDelete());
							
				//TODO Create code to delete notes associated with event
				
				answer = String.format("Event " + deleteEventObject.getEventToDelete() + "has been deleted.");
				
			}else{
				answer = "Your are not the owner of this Event";
				
			}
		}
		
		return answer;
	}
}
