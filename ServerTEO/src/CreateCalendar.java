import java.sql.ResultSet;
import java.sql.SQLException;

import shared.CreateCalendarObject;
import shared.CreateCalendarReturnObject;

import com.google.gson.Gson;

import model.QueryBuild.*;
/**
 * The CreateCalendar Class is responsible for creating new calendars in the server database.
 * 
 * @author Esben
 *
 */
public class CreateCalendar{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	private Gson gson = new Gson();
	private ResultSet resultSet;
	private CreateCalendarReturnObject ccro = new CreateCalendarReturnObject();//The object to be returned as a String
	private String answer = ""; // the String to be returned
	private boolean created = false; //To notify if the calendar is created
	private String message;// The description of what has happened
	boolean doesExist = false;
	
	/**
	 * The execute method uses the information in the incoming object to create a new calendar.
	 * It needs to make sure that the new calendar has a unique name.
	 * It then creates a new calendar and sets all the parameters for the new calendar.
	 * It also sets author and user rights for the calendar.
	 * @param createCalendarObject
	 * @return
	 * @throws SQLException
	 */
	
	public String execute(CreateCalendarObject createCalendarObject) throws SQLException{
		
		doesExist = authenticateNewCalender(createCalendarObject.getCalendarName());
		
		if (doesExist == false){
			String [] keys = {"calendarName","imported","privatePublic"};
			String [] values = {
					createCalendarObject.getCalendarName(), 
					"0",//A newly created calendar is never imported.
					createCalendarObject.getPrivatePublic()
					};
			
			queryBuilder.insertInto(dbConfig.getCalendar(), keys).values(values).Execute();
			
			resultSet = queryBuilder.selectFrom("calendars").where("calendarname", "=", createCalendarObject.getCalendarName()).ExecuteQuery();
			resultSet.next();
			String newCalendarID = resultSet.getString("calendarid");
			
			for(String n : createCalendarObject.getUsers())//sets initial subscribers for the new calendar
			{
				resultSet = queryBuilder.selectFrom("users").where("username", "=", n).ExecuteQuery();
				resultSet.next();
				String userID = resultSet.getString("userid");
				String[] keysUser = {"userid", "calendarid"};
				String[] valuesUser = {
						userID,
						newCalendarID
				};
				queryBuilder.insertInto("Subscription", keysUser).values(valuesUser).Execute();
			}
			
			for(String n : createCalendarObject.getAuthors())//sets initial authors for the new calendar
			{
				resultSet = queryBuilder.selectFrom("users").where("username", "=", n).ExecuteQuery();
				resultSet.next();
				String authorID = resultSet.getString("userid");
				String[] keysAuthor = {"userid", "calendarid"};
				String[] valuesAuthor = {
						authorID,
						newCalendarID
				};
				queryBuilder.insertInto("AutherRights", keysAuthor).values(valuesAuthor).Execute();
			}
			created = true;
			message = "Calendar successfully created";
			
			ccro.setCreated(created);
			ccro.setMessage(message);
					
		}else if(doesExist == true){
			created = false;
			message = "Calendar already exsists";
			ccro.setCreated(created);
			ccro.setMessage(message);
			
		}else{
			created = false;
			message = "System error";
			ccro.setCreated(created);
			ccro.setMessage(message);
		}
		
		answer = gson.toJson(ccro);
		
		return answer;
	}
	
	/**
	 * The authenticateNewCalender method cheks the database for an existing calendar with an identical name.
	 * @param newCalenderName
	 * @return
	 * @throws SQLException
	 */
	
	// Why is this needed??? because the name is used to couple the user and calendar. But this might be avoided?
	public boolean authenticateNewCalender(String newCalenderName) throws SQLException
	{
		boolean doesExist = false;
		
		resultSet = queryBuilder.selectFrom(dbConfig.getCalendar()).where("calendarname", "=", newCalenderName).ExecuteQuery();
				
		if(resultSet.next())
		{
			doesExist = true;
		}
		
		return doesExist;
	}
}
