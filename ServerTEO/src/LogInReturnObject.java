
public class LogInReturnObject implements java.io.Serializable {
	
	/**
	 * This is the transfer object of the login information to be returned after login verification
	 */
	private static final long serialVersionUID = 1274538738336421428L;
	private String overallID = "logInReturn";
	private boolean logOn;
	private String explination;
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public boolean isLogOn() {
		return logOn;
	}
	public void setLogOn(boolean logOn) {
		this.logOn = logOn;
	}
	public String getExplination() {
		return explination;
	}
	public void setExplination(String explination) {
		this.explination = explination;
	}
	
}
