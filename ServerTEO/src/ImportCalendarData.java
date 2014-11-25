

import Shared.Event;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Model;
import model.QueryBuild.QueryBuilder;

/**
 * Created by jesperbruun on 13/10/14.
 */
public class ImportCalendarData extends Model{
	private QueryBuilder qb = new QueryBuilder();
	private EncryptUserID e = new EncryptUserID();
	private Gson gson = new Gson();
	private ResultSet resultSet;
	private String json;
	private String calendarID;
	private String userID;

	//henter data fra URL og l??er ind til en string

	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	//Nu har vi alle data liggende i en string (JSON). 
	//Saa bruger vi Google's udviklede library Json string. den kan lave det om til java objekter
	//Events laver en arraylist af Event

	/**
	 * Allows server to retrieve CBS's calendar and then save it in the database.
	 * @throws Exception
	 */
	public void importCalendar(String userName) throws SQLException {

		json = fromCBSCalendar(userName);

		Events events = (Events) gson.fromJson(json, Events.class);

		for(Event e : events.getEvents()){
//			qb = new QueryBuilder();//prøver at instansiere en ny qb for hver event for at forhindre for mange forbindelser

			saveCalendar(e);//calls the method to save the calendar

			//Subscriping user to calenders
			if(isSubscribed(e, userName) == false){

				qb.insertInto("subscription", new String[] {"calendarID", "userID"}).values(new String[] {calendarID, userID}).Execute();
			}

			saveEvent(e);//saves the current event to the database

		}
	}

	/**
	 * Get URL From calendar.cbs.dk -> Subscribe -> change URL to end with .json
	 * Encrypt hash from
	 */
	public String fromCBSCalendar(String userName){
		String key = e.crypt(userName + e.getHASHKEY());
		json = "";
		try {
			json = readUrl("http://calendar.cbs.dk/events.php/"+userName+"/"+key+".json");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return json;
	}
	/**
	 * Method for insert or update the calendar in the database.
	 * @param e
	 */
	public void saveCalendar(Event e) throws SQLException{
		//To create the calendar if not in database
		String[] fields = {
				"calendarname",
				"privatepublic",
				"imported"
		};
		String[] values = {
				e.getActivityid(),
				"1",
				"1"
		};
		resultSet = qb.selectFrom("calendars").where("calendarname", "=", e.getActivityid()).ExecuteQuery();
		if(resultSet.next() == false){

			qb.insertInto("calendars", fields).values(values).Execute();

		}else{
			qb.update("calendars",fields, values).where("calendarname", "=", e.getActivityid()).Execute();
		}
	}
	/**
	 * This method checks to see if the user is subscribed to the specific calendar in the database.
	 * @param e
	 * @return
	 */

	public boolean isSubscribed(Event e, String userName) throws SQLException{
		resultSet = qb.selectFrom("calendars").where("calendarname", "=", e.getActivityid()).ExecuteQuery();
		resultSet.next();
		calendarID = resultSet.getString("calendarid");
		resultSet = qb.selectFrom("users").where("username", "=",userName).ExecuteQuery();
		resultSet.next();
		userID = resultSet.getString("userid");

		resultSet = qb.selectFrom("subscription").where("userid", "=", userID).ExecuteQuery();

		boolean subscribtion = false;

		while(resultSet.next()){//check if user is subscribed to the calendar
			if(resultSet.getString("calendarid").equals(calendarID)){
				subscribtion = true;
			}
		}
		return subscribtion;
	}

	/**
	 * Method for saving a specific event in the database
	 * @param e
	 * @throws SQLException
	 */

	public void saveEvent(Event e) throws SQLException{
		resultSet = qb.selectFrom("events").where("externaleventID", "=", e.getEventid()).ExecuteQuery();

		String[] fields = {
				"calendarID",
				"externaleventID",
				"type",
				"eventname",
				"description",
				"start",
				"end",
				"location"
		};

		String[] values = {
				calendarID,
				e.getEventid(),
				e.getType(),
				e.getTitle(),
				e.getDescription(),
				arrayToString(e.getStart()),
				arrayToString(e.getEnd()),
				e.getLocation()
		};

		if(resultSet.next()){
			qb.update("events", fields, values).where("externaleventID", "=", e.getEventid()).Execute();
		}else{
			qb.insertInto("events", fields).values(values).Execute();
		}
	}

	/**
	 * Method for converting an array list of Strings containing year, day, month, hour, minute to a mysql timedate object to a single String.
	 * @param aL
	 * @return
	 */

	public String arrayToString(ArrayList<String> aL){
		String answer = "";

		for (int i = 0; i < aL.size(); i++){
			answer += aL.get(i); 
			if(i < 2){
				answer += "-";
			}
			if(i == 2){
				answer += " ";
			}
			if (i > 2 && (i+1) != aL.size() && (i+1) < 6){
				answer += ":";
			}
			if((i+1) == aL.size() && aL.size() < 6){
				answer += ":00";
			}
		}
		return answer;
	}
}
