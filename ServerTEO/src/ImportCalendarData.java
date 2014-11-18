

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.QueryBuild.QueryBuilder;

/**
 * Created by jesperbruun on 13/10/14.
 */
public class ImportCalendarData {
	private QueryBuilder qb = new QueryBuilder();
	private EncryptUserID e = new EncryptUserID();
	private Gson gson = new Gson();
	private ResultSet resultSet;
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
     * Allows client to retrieve CBS's calendar and then access it.
     * @throws Exception
     */
    public void importCalendar(String userID) throws SQLException {

        /**
         * Get URL From calendar.cbs.dk -> Subscribe -> change URL to end with .json
         * Encrypt hash from
         */
    	String key = e.crypt(userID + e.getHASHKEY());
        String json = "";
		try {
			json = readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+key+".json");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
        Events events = (Events) gson.fromJson(json, Events.class);
        
        for(Event e : events.getEvents()){
        	resultSet = qb.selectFrom("calendar").where("calendarID", "=", e.getActivityid()).ExecuteQuery();
        	
        	//To create the calendar if not in database
        	if(resultSet.next()){
        		
        	}else{
        		String[] fields = {
        				"calendarID",
        				"privatepublic",
        				"imported"
        		};
        		String[] values = {
        				e.getActivityid(),
        				"1",
        				"1"
        		};
        		qb.insertInto("calendars", fields).values(values).Execute();
        		
        		//Subscriping user to calenders
        		qb.insertInto("subscription", new String[] {"calendarID", "userID"}).values(new String[] {e.getActivityid(), userID}).Execute();
        		
        	}
        	
        	resultSet = qb.selectFrom("events").where("eventID", "=", e.getEventid()).ExecuteQuery();
        	
        	String[] fields = {
    				"calendarID",
    				"eventID",
    				"type",
    				"eventname",
    				"descripion",
    				"start",
    				"end",
    				"location"
    		};
        	
    		String[] values = {
    				e.getActivityid(),
    				e.getEventid(),
    				e.getType(),
    				e.getTitle(),
    				e.getDescription(),
    				arrayToString(e.getStart()),
    				arrayToString(e.getEnd()),
    				e.getLocation()
    		};
        	        	
        	if(resultSet.next()){
        		qb.update("events", fields, values).where("eventID", "=", e.getEventid()).Execute();
        	}else{
        		qb.insertInto("events", fields).values(values).Execute();
        	}
        }

        //tester events activityID's
//        for (int i = 0; i < events.getEvents().size(); i++){
//            System.out.println(events.getEvents().get(i).getActivityid());
//        }
    }
    
    public String arrayToString(ArrayList<String> aL){
    	String answer = "";
    	
    	for (int i = 0; i < aL.size(); i++){
    		answer += aL.get(i); 
			if((i+1) == aL.size()){
				
			}else{
				answer += ", ";
			}
    	}
    	
    	return answer;
    }
}
