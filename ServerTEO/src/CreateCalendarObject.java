
public class CreateCalendarObject implements java.io.Serializable
{
	private static final long serialVersionUID = -580896570793664110L;
	private String overallID = "deleteEvent";
	private String calendarName;
	
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

}