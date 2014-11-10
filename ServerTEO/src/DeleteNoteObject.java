
public class DeleteNoteObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1026261677650219409L;
	private String overallID = "deleteNote";
	private String noteToDelete;
	private String userID;
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getNoteToDelete() {
		return noteToDelete;
	}
	public void setNoteToDelete(String noteToDelete) {
		this.noteToDelete = noteToDelete;
	}
	public String getAuthNote() {
		return userID;
	}
	public void setAuthNote(String authNote) {
		this.userID = authNote;
	}
	
}
