
public class CreateCalendarObject implements java.io.Serializable
{
	private static final long serialVersionUID = -580896570793664110L;
	private String overallID = "deleteEvent";
	private String calendarName;
	private int privatePublic;
<<<<<<< HEAD
	private boolean imported;
	
=======
	private String createdBy;
	//private int active;
>>>>>>> origin/master
	
	
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
<<<<<<< HEAD
	
	public boolean getCreatedBy() {
		return imported;
=======
//	public int getActive() {
//		return active;
//	}
//	public void setActive(int active) {
//		this.active = active;
//	}
	public String getCreatedBy() {
		return createdBy;
>>>>>>> origin/master
	}
	public void setCreatedBy(boolean imported) {
		this.imported = imported;
	}

}
