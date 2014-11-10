import java.sql.SQLException;

import model.Model;
import model.QueryBuild.*;
public class CreateCalendar extends Model{
	private DatabaseTableConfigurations dbConfig = new DatabaseTableConfigurations();
	
	private QueryBuilder queryBuilder = new QueryBuilder();
	
	public String execute(CreateCalendarObject createCalendarObject) throws SQLException{
		String answer = "";
		
		boolean doesExist = authenticateNewCalender(createCalendarObject.getCalendarName());
		
		if ( doesExist == false){
			String [] keys = {"Name","CreatedBy","PrivatePublic"};
			String [] values = {
					createCalendarObject.getCalendarName(), 
//					Integer.toString(createCalendarObject.getActive()), 
					createCalendarObject.getCreatedBy(), 
					Integer.toString(createCalendarObject.getPrivatePublic())
					};
			
			queryBuilder.update(dbConfig.getCalendar(), keys, values);
			
			answer = "Calendar successfully created";
		
		}else if(doesExist == true){
			answer = "Calendar already exsists";
		}else{
			answer = "System error";
		}
		
		return answer;
	}
	
	public boolean authenticateNewCalender(String newCalenderName) throws SQLException
	{
		boolean authenticate = false;
		
		resultSet = queryBuilder.selectFrom(dbConfig.getCalendar()).where("name", "=", newCalenderName).ExecuteQuery();
				
		while(resultSet.next())
		{
			authenticate = true;
		}
		return authenticate;
	}

}
