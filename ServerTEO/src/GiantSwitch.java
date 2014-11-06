import java.sql.SQLException;

import quote.Quotes;
import model.calendar.Event;
import model.note.Note;
import JsonClasses.AuthUser;
import JsonClasses.CalendarInfo;
import JsonClasses.Createcalendar;
import JsonClasses.Deletecalendar;

import com.google.gson.*;

import databaseMethods.SwitchMethods;

public class GiantSwitch {
	
	
	
	public String GiantSwitchMethod(String jsonString) throws SQLException {

		//Events eventsKlasse = new Events(0, 0, 0, jsonString, jsonString, jsonString, jsonString, jsonString);

		Note noteKlasse = new Note();
		//ForecastModel forecastKlasse = new ForecastModel();
		Quotes QOTDKlasse = new Quotes();
		SwitchMethods SW = new SwitchMethods();
		
		Gson gson = new GsonBuilder().create();
		String answer = "";	
		//Creates a switch which determines which method should be used. Methods will be applied later on
		
		switch (Determine(jsonString)) {
		//If the Json String contains one of the keywords below, run the relevant method.

		/************
		 ** COURSES **
		 ************/

//		case "importCalendar":
//			System.out.println("Recieved importCourse");
//			//skal laves
//			break;

		/**********
		 ** LOGIN **
		 **********/
		case "logIn":
			AuthUser AU = (AuthUser)gson.fromJson(jsonString, AuthUser.class);
			System.out.println("Recieved logIn");
			answer = SW.authenticate(AU.getAuthUserEmail(), AU.getAuthUserPassword(), AU.getAuthUserIsAdmin());
			break;

		case "logOut":
			System.out.println("Recieved logOut");
			break;

		/*************
		 ** CALENDAR **
		 *************/
		case "createcalendar":
			CreateCalendar CC = (CreateCalendar)gson.fromJson(jsonString, CreateCalendar.class);
			System.out.println(CC.getcalendarName()+ "Den har lagt det nye ind i klassen");
			answer = SW.createNewcalendar(CC.getUserName(), CC.getcalendarName(), CC.getPublicOrPrivate());
			break;
		
		case "deletecalendar":
			DeleteCalendar DC = (DeleteCalendar)gson.fromJson(jsonString, DeleteCalendar.class);
			System.out.println(DC.getcalendarName()+ "Den har lagt det nye ind i klassen");
			answer = SW.deletecalendar(DC.getUserName(), DC.getcalendarName());
			break;
		
		case "saveImportedcalendar":
			
			
			break;
			
		case "getcalendar":
			GetCalendarObject gcObject = gson.fromJson(jsonString, GetCalendarObject.class);
			GetCalendar getCalendar = new GetCalendar();
			answer = getCalendar.execute(gcObject);
			
			System.out.println("Recieved getcalendar");
			break;

//		case "getEvents":
//			System.out.println("Recieved getEvents");
//			break;

		case "createEvent":
			System.out.println("Recieved saveEvent");
			break;

//		case "getEventInfo":
//			System.out.println("Recieved getEventInfo");
//			break;
			
		case "deleteEvent":
			DeleteEventObject deleteEO = gson.fromJson(jsonString, DeleteEventObject.class);
			DeleteEvent deleteE = new DeleteEvent();
			deleteE.execute(deleteEO);
			System.out.println("Recieved deleteEvent");
		
		case "saveNote":
			System.out.println("Recieved saveNote");
			break;

		case "getNote":
			System.out.println("Recieved getNote");
			break;
			
		case "deleteNote":
			System.out.println("Recieved deleteNote");
			break;

		/**********
		 ** QUOTE **
		 **********/
		case "getQuote":

		answer = QOTDKlasse.getQuote();
			System.out.println(answer);
			
			break;

		/************
		 ** WEATHER **
		 ************/

		case "getClientForecast":
			System.out.println("Recieved getClientForecast");
			break;
		
		default:
			System.out.println("Error");
			break;
		}
		return answer;
		
	}

	//Creates a loooong else if statement, which checks the JSon string which keyword it contains, and returns the following 
	//keyword if
	public String Determine(String ID) {

		if (ID.contains("getEvents")) {
			return "getEvents";
		} else if (ID.contains("getEventInfo")) {
			return "getEventInfo";
		} else if (ID.contains("saveNote")) {
			return "saveNote";
		} else if (ID.contains("getNote")) {
			return "getNote";
		} else if (ID.contains("deleteNote")){
			return "deleteNote";
		}else if  (ID.contains("deletecalendar")){
			return "deletecalendar";
		} else if (ID.contains("getClientForecast")) {
			return "getClientForecast";
		} else if (ID.contains("saveImportedcalendar")) {
			return "saveImportedcalendar";
		}else if (ID.contains("importCourse")) {
			return "importCourse";
		} else if (ID.contains("exportCourse")) {
			return "exportCourse";
		} else if (ID.contains("getQuote")) {
			return "getQuote";
		} else if (ID.contains("logIn")) {
			return "logIn";
		} else if (ID.contains("logOut")) {
			return "logOut";
		} else if (ID.contains("getcalendar")) {
			return "getcalendar";
		} else if (ID.contains("createEvent")) {
			return "createEvent";
		} else if (ID.contains("deleteEvent")) {
			return "deleteEvent"; 
		} else if (ID.contains("createcalendar")) {
			return "createcalendar";
		}

		else
			return "error";
	}
	

}
