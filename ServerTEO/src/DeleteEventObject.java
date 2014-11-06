
public class DeleteEventObject implements java.io.Serializable{
	
	private static final long serialVersionUID = -4749520182118516370L;
	private String overallID = "deleteEvent";
	private String eventToDelete;
	private String authEvent;
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getEventToDelete() {
		return eventToDelete;
	}
	public void setEventToDelete(String eventToDelete) {
		this.eventToDelete = eventToDelete;
	}
	public String getAuthEvent() {
		return authEvent;
	}
	public void setAuthEvent(String authEvent) {
		this.authEvent = authEvent;
	}

}
