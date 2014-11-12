import java.sql.SQLException;

import com.google.gson.Gson;

import model.Model;
import model.QueryBuild.*;

public class CreateCalendar extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	private QueryBuilder queryBuilder = new QueryBuilder();
	private Gson gson = new Gson();
	
	public String execute(CreateCalendarObject createCalendarObject) throws SQLException{
		String answer = "";
		
		boolean created = false;
		String message;
		CreateCalendarReturnObject ccro = new CreateCalendarReturnObject();
		
		boolean doesExist = authenticateNewCalender(createCalendarObject.getCalendarName());
		
		if (doesExist == false){
			String [] keys = {"calendarName","imported","privatePublic"};
			String [] values = {
					createCalendarObject.getCalendarName(), 
					"false",//A newly created calendar is never imported.
					createCalendarObject.getPrivatePublic()
					};
			
			
			
			queryBuilder.insertInto(dbConfig.getCalendar(), keys).values(values).Execute();
			
			String newCalendarID = queryBuilder.selectFrom("calendars").where("calendarname", "=", createCalendarObject.getCalendarName()).ExecuteQuery().getString("calendarid");
			
			for(String n : createCalendarObject.getUsers())//sets initial supscibers for the new calendar
			{
				
				String[] keysUser = {"userid", "calendarid"};
				String[] valuesUser = {
						queryBuilder.selectFrom("users").where("email", "=", n).ExecuteQuery().getString("userid"),
						queryBuilder.selectFrom("calendars").where("calendarname", "=", newCalendarID).ExecuteQuery().getString("calendarid")
				};
				queryBuilder.insertInto("Subsciption", keysUser).values(valuesUser).Execute();
			}
			
			for(String n : createCalendarObject.getAuthors())//sets initial authors for the new calendar
			{
				
				String[] keysAuthor = {"userid", "calendarid"};
				String[] valuesAuthor = {
						queryBuilder.selectFrom("users").where("email", "=", n).ExecuteQuery().getString("userid"),
						queryBuilder.selectFrom("calendars").where("calendarname", "=", newCalendarID).ExecuteQuery().getString("calendarid")
				};
				queryBuilder.insertInto("Subsciption", keysAuthor).values(valuesAuthor).Execute();
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
	
	public boolean authenticateNewCalender(String newCalenderName) throws SQLException
	{
		boolean doesExist = false;
		
		resultSet = queryBuilder.selectFrom(dbConfig.getCalendar()).where("name", "=", newCalenderName).ExecuteQuery();
				
		while(resultSet.next())
		{
			doesExist = true;
		}
		return doesExist;
	}
}
