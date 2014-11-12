import java.util.ArrayList;


public class CreateEventObject implements java.io.Serializable

{
	private static final long serialVersionUID = 603624733895783558L;
	private String overallID = "createEvent";
	public String type;
	public String eventName;
	private String description;
	public String location;
	public String createdby;
	public String calendarName;
	public ArrayList<String> start;
	public ArrayList<String> end;
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public ArrayList<String> getStart() {
		return start;
	}
	public void setStart(ArrayList<String> start) {
		this.start = start;
	}
	public ArrayList<String> getEnd() {
		return end;
	}
	public void setEnd(ArrayList<String> end) {
		this.end = end;
	}
	
	

	
}
