
public class LogInObject implements java.io.Serializable
{

	private static final long serialVersionUID = -5488436560146102137L;
	private String overallID = "getCalendar";
	private String authUserEmail;
	private String authPassword;
	private boolean isAdmin;
	
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getAuthUsername() {
		return authUserEmail;
	}
	public void setAuthUsername(String authUsername) {
		this.authUserEmail = authUsername;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}