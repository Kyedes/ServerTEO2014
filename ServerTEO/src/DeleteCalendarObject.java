
public class DeleteCalendarObject implements java.io.Serializable
{
	private static final long serialVersionUID = 4429483023071064697L;
	private String overallID = "getCalendar";
	private String calendarToDelete;
	private String authCalendar;
	
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getCalendarToDelete() {
		return calendarToDelete;
	}
	public void setCalendarToDelete(String calendarToDelete) {
		this.calendarToDelete = calendarToDelete;
	}
	public String getAuthCalendar() {
		return authCalendar;
	}
	public void setAuthCalendar(String authCalendar) {
		this.authCalendar = authCalendar;
	}

}
