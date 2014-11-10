
public class CreateEventObject implements java.io.Serializable

{
	private static final long serialVersionUID = 603624733895783558L;
	private String overallID = "deleteEvent";
	public String name;
	public String location;
	public String timeStart;
	public String timeFinished;
	public String author;
	public String calendarName;
	
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeFinished() {
		return timeFinished;
	}
	public void setTimeFinished(String timeFinished) {
		this.timeFinished = timeFinished;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	
}
