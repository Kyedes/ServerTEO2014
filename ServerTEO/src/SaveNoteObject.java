
public class SaveNoteObject implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1269487144151426011L;
	private String overallID = "getCalendar";
	private String noteAuthor;
	private String noteContent;
	private String noteEvent;
	
	
	public String getOverallID() {
		return overallID;
	}
	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	
	public String getNoteAuthor() {
		return noteAuthor;
	}
	public void setNoteAuthor(String noteAuthor) {
		this.noteAuthor = noteAuthor;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getNoteEvent() {
		return noteEvent;
	}
	public void setNoteEvent(String noteEvent) {
		this.noteEvent = noteEvent;
	}
	
	

}
