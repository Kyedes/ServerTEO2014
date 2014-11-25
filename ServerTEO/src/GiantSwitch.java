import java.sql.SQLException;

//import quote.Quotes;
//import model.calendar.Event;
//import model.note.Note;
//import JsonClasses.AuthUser;
//import JsonClasses.CalendarInfo;
//import JsonClasses.Createcalendar;
//import JsonClasses.Deletecalendar;









import Shared.CreateCalendarObject;
import Shared.CreateEventObject;
import Shared.DeleteCalendarObject;
import Shared.DeleteEventObject;
import Shared.DeleteNoteObject;
import Shared.GetCalendarObject;
import Shared.GetNoteObject;
import Shared.SaveNoteObject;

import com.google.gson.*;

//import databaseMethods.SwitchMethods;

public class GiantSwitch {
	
	
	
	public String GiantSwitchMethod(String jsonString) throws SQLException {

		//Events eventsKlasse = new Events(0, 0, 0, jsonString, jsonString, jsonString, jsonString, jsonString);

//		Note noteKlasse = new Note();
		//ForecastModel forecastKlasse = new ForecastModel();
//		Quotes QOTDKlasse = new Quotes();
//		SwitchMethods SW = new SwitchMethods();
		
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
			LogInObject logInObject = (LogInObject) gson.fromJson(jsonString, LogInObject.class);
			LogIn logIn = new LogIn();
			answer = logIn.execute(logInObject);
			
			
//			AuthUser AU = (AuthUser)gson.fromJson(jsonString, AuthUser.class);
//			System.out.println("Recieved logIn");
//			answer = SW.authenticate(AU.getAuthUserEmail(), AU.getAuthUserPassword(), AU.getAuthUserIsAdmin());
			break;

//			case "logOut":
//			System.out.println("Recieved logOut");
//			break;

		/*************
		 ** CALENDAR **
		 *************/
		case "createcalendar":
			CreateCalendarObject createCalendarObject = (CreateCalendarObject) gson.fromJson(jsonString, CreateCalendarObject.class);
			CreateCalendar createCalendar = new CreateCalendar();
			answer = createCalendar.execute(createCalendarObject);
			
//			System.out.println(CC.getcalendarName()+ "Den har lagt det nye ind i klassen");
//			answer = SW.createNewcalendar(CC.getUserName(), CC.getcalendarName(), CC.getPublicOrPrivate());
			break;
		
		case "deletecalendar":
			DeleteCalendarObject deleteCalendarObject = (DeleteCalendarObject)gson.fromJson(jsonString, DeleteCalendarObject.class);
			DeleteCalendar deleteCalendar  = new DeleteCalendar();
			answer = deleteCalendar.execute(deleteCalendarObject);
			
//			System.out.println(DC.getcalendarName()+ "Den har lagt det nye ind i klassen");
//			answer = SW.deletecalendar(DC.getUserName(), DC.getcalendarName());
			break;
		
//		case "saveImportedcalendar":
//			break;
			
		case "getCalendar":
			GetCalendarObject getCalendarObject = (GetCalendarObject) gson.fromJson(jsonString, GetCalendarObject.class);
			GetCalendar getCalendar = new GetCalendar();
			answer = getCalendar.execute(getCalendarObject);
			
//			System.out.println("Recieved getcalendar");
			break;

//		case "getEvents":
//			System.out.println("Recieved getEvents");
//			break;

		case "createEvent":
			CreateEventObject createEventObject = (CreateEventObject) gson.fromJson(jsonString, CreateEventObject.class);
			CreateEvent createEvent = new CreateEvent();
			answer = createEvent.execute(createEventObject);
//			System.out.println("Recieved saveEvent");
			break;

//		case "getEventInfo":
//			System.out.println("Recieved getEventInfo");
//			break;
			
		case "deleteEvent":
			DeleteEventObject deleteEO = (DeleteEventObject) gson.fromJson(jsonString, DeleteEventObject.class);
			DeleteEvent deleteE = new DeleteEvent();
			answer = deleteE.execute(deleteEO);
//			System.out.println("Recieved deleteEvent");
			break;
		
		case "saveNote":
			SaveNoteObject saveNoteObject = (SaveNoteObject) gson.fromJson(jsonString, SaveNoteObject.class);
			SaveNote saveNote = new SaveNote();
			answer = saveNote.execute(saveNoteObject);
//			System.out.println("Recieved saveNote");
			break;

		case "getNote":
			GetNoteObject getNoteObject = (GetNoteObject) gson.fromJson(jsonString, GetNoteObject.class);
			GetNote getNote = new GetNote();
			answer = getNote.execute(getNoteObject);
//			System.out.println("Recieved getNote");
			break;
			
		case "deleteNote":
			DeleteNoteObject deleteNoteObject = (DeleteNoteObject) gson.fromJson(jsonString, DeleteNoteObject.class);
			DeleteNote deleteNote = new DeleteNote();
			answer = deleteNote.execute(deleteNoteObject);
//			System.out.println("Recieved deleteNote");
			break;

		/**********
		 ** QUOTE **
		 **********/
		case "getQuote":

			Quotes quotes = new Quotes();
			
			answer = quotes.getQuote();
			
//			System.out.println(answer);
			
			break;

		/************
		 ** WEATHER **
		 ************/

		case "getClientForecast":
			System.out.println("Recieved getClientForecast");
			break;
		
		default:
			answer = "System Error: overallID not recogniced";
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
		} else if (ID.contains("getCalendar")) {
			return "getCalendar";
		} else if (ID.contains("createEvent")) {
			return "createEvent";
		} else if (ID.contains("deleteEvent")) {
			return "deleteEvent"; 
		} else if (ID.contains("createCalendar")) {
			return "createCalendar";
		}

		else
			return "error";
	}
}
