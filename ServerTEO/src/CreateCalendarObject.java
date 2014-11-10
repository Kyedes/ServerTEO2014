
public class CreateCalendarObject implements java.io.Serializable
{
	private static final long serialVersionUID = -580896570793664110L;
	private String overallID = "deleteEvent";
	private String calendarName;
	private int privatePublic;
	private boolean imported;
	
	
	
	public int getPrivatePublic() {
		return privatePublic;
	}
	public void setPrivatePublic(int privatePublic) {
		this.privatePublic = privatePublic;
	}
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
	
	public boolean getCreatedBy() {
		return imported;
	}
	public void setCreatedBy(boolean imported) {
		this.imported = imported;
	}

}
