import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;

public class DeleteCalendar extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	
	public String execute(DeleteCalendarObject deleteCalendarObject) throws SQLException{
		String answer = "";
		
		resultSet = queryBuilder.selectFrom(dbConfig.getCalendar()).where("Name", "=", deleteCalendarObject.getCalendarToDelete()).ExecuteQuery();
		
		while(resultSet.next()){
			String [] value = {"CreatedBy"};
			
			//TODO compare list of user/calendar with user and calendar name
			
			String calendarAuthor = queryBuilder.selectFrom(value, dbConfig.getCalendar()).where("Name", "=", deleteCalendarObject.getCalendarToDelete()).ExecuteQuery().toString();
			
			if(deleteCalendarObject.getAuthCalendar().equalsIgnoreCase(calendarAuthor)){
				
				
				
				queryBuilder.deleteFrom(dbConfig.getCalendar()).where("Name", "=", deleteCalendarObject.getCalendarToDelete());
							
				//TODO Create code to delete events associated with calendar
				
				answer = String.format("Calendar " + deleteCalendarObject.getCalendarToDelete() + "has been deleted.");
				
			}else{
				answer = "Your are not the owner of this calendar";
				
			}
		}
		
		
		return answer;
	}

}
