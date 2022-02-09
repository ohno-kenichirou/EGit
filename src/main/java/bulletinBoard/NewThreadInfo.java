package bulletinBoard;

public class NewThreadInfo {

	private String title;
	private String category;
	private String userName;
	private String comment;
	
	public NewThreadInfo(String title, String category, String userName, String comment) {
		this.title = title;
		this.category = category;
		this.userName = userName;
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getUserName() {
		return userName;
	}
	
	public String getComment() {
		return comment;
	}
	
}
