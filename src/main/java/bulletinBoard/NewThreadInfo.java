package bulletinBoard;

public class NewThreadInfo {

	private String title;
	private String category;
	private String comment;
	
	public NewThreadInfo(String title, String category, String comment) {
		this.title = title;
		this.category = category;
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getComment() {
		return comment;
	}
	
}
