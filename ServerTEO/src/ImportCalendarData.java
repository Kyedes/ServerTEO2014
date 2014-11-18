

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;

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
    public Events getDataFromCalendar(String userID) throws Exception {

        /**
         * Get URL From calendar.cbs.dk -> Subscribe -> change URL to end with .json
         * Encrypt hash from
         */
    	String key = e.crypt(userID + e.getHASHKEY());
        String json = readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+key+".json");

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
    				e.getStart().toString(),//TODO skal testes og måske laves om, Husk databasen
    				e.getEnd().toString(),
    				e.getLocation()        				  
    		};
        	        	
        	if(resultSet.next()){
        		qb.update("events", fields, values).where("eventID", "=", e.getEventid()).Execute();
        	}else{
        		qb.insertInto("events", fields).values(values).Execute();
        	}
        }

        return events;

        //tester events activityID's
//        for (int i = 0; i < events.getEvents().size(); i++){
//            System.out.println(events.getEvents().get(i).getActivityid());
//        }
    }
}
