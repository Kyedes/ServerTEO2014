import java.sql.SQLException;

import shared.DeleteCalendarObject;
import model.Model;
import model.QueryBuild.QueryBuilder;

public class DeleteCalendar extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();

	/**
	 * The execute method checks if the user have rights to delete the calendar,
	 *  and only if this is the case is the calendar deleted. First the the notes of each individual event is deleted;
	 *   then each event, pertaining to the calendar, gets deleted;
	 *   then the calendar is deleted; lastly the subscribers and author rights are removed.
	 * @param deleteCalendarObject
	 * @return
	 * @throws SQLException
	 */
	
	public String execute(DeleteCalendarObject deleteCalendarObject) throws SQLException{
		String answer = "";

		String [] valueCalendar = {"calendarID"};

		String [] valueUser = {"userID"};

		String [] valueImport = {"import"};
		
		String[] valuesEvent = {"eventID"};

		resultSet = queryBuilder.selectFrom(valueCalendar, "Calendars").where("CalendarName", "=", deleteCalendarObject.getCalendarToDelete()).ExecuteQuery();
		resultSet.next();
		String calendarID = resultSet.toString();
		
		//TODO Strings here but integer in database?
		
		resultSet = queryBuilder.selectFrom(valueUser, "Users").where("UserName", "=", deleteCalendarObject.getuserID()).ExecuteQuery();
		resultSet.next();
		int userID = resultSet.getInt("userID");
				
		boolean author = false;

		resultSet = queryBuilder.selectFrom(valueImport, "Calendars").where("calendarID", "=", calendarID).ExecuteQuery();
		resultSet.next();
		boolean imported = resultSet.getBoolean("imported");
		
		if(imported == false){
			resultSet = queryBuilder.selectFrom(valueUser, "AutherRights").where("CalendarID", "=", calendarID).ExecuteQuery();

			while(resultSet.next()){

				//TODO Check if resultset is used correctly
				if(resultSet.getInt("userID") == userID){
					author = true;
				}
			}

			if(author){
				
				resultSet = queryBuilder.selectFrom(valuesEvent, "Events").where("CalendarID", "=", calendarID).ExecuteQuery();
				while(resultSet.next()){
					String eventID = resultSet.getString("eventID");
					queryBuilder.deleteFrom("Notes").where("eventID", "=", eventID);
				}
				
				queryBuilder.deleteFrom(dbConfig.getEvents()).where("CalendarID", "=", calendarID);
				queryBuilder.deleteFrom(dbConfig.getCalendar()).where("CalendarID", "=", calendarID);
				queryBuilder.deleteFrom("subscription").where("CalendarID", "=", calendarID);
				queryBuilder.deleteFrom("autherrights").where("CalendarID", "=", calendarID);
				
				
				answer = String.format("Calendar " + deleteCalendarObject.getCalendarToDelete() + "has been deleted, along with all associated events and notes.");

			}else{
				answer = "You do not have the rights to delete this calendar.";
			}
		}else{
			answer = "This is an imported calendar and cannot be deleted.";
		}
		return answer;
	}

}


