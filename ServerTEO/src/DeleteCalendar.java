import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;

public class DeleteCalendar extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();

	public String execute(DeleteCalendarObject deleteCalendarObject) throws SQLException{
		String answer = "";

		String [] valueCalendar = {"calendarID"};

		String [] valueUser = {"userID"};

		String [] valueImport = {"import"};

		String calendarID = queryBuilder.selectFrom(valueCalendar, "Calendar").where("CalendarName", "=", deleteCalendarObject.getCalendarToDelete()).ExecuteQuery().toString();

		//TODO Strings here but integer in database?
		
		int userID = queryBuilder.selectFrom(valueUser, "Users").where("UserName", "=", deleteCalendarObject.getAuthCalendar()).ExecuteQuery().getInt("userID");
		
		boolean auther = false;

		boolean imported = queryBuilder.selectFrom(valueImport, "Calendar").where("calendarID", "=", calendarID).ExecuteQuery().getBoolean("imported");

		if(imported == false){
			resultSet = queryBuilder.selectFrom(valueUser, "AutherCalendar").where("CalendarID", "=", calendarID).ExecuteQuery();

			while(resultSet.next()){

				//TODO Check if resultset is used correctly
				if(resultSet.getInt("userID") == userID){
					auther = true;
				}
			}

			if(auther){

				queryBuilder.deleteFrom(dbConfig.getCalendar()).where("CalendarID", "=", calendarID);

				queryBuilder.deleteFrom(dbConfig.getEvents()).where("CalendarID", "=", calendarID);

				answer = String.format("Calendar " + deleteCalendarObject.getCalendarToDelete() + "has been deleted, along with all associated events.");

			}else{
				answer = "You do not have the rights to delete this calendar.";
			}
		}else{
			answer = "This is an imported calendar and cannot be deleted.";
		}
		return answer;
	}

}


