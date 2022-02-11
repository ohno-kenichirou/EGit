package bulletinBoard;

public class NewThreadInfo {

	private String title;
	private int categoryId;
	private String comment;
	
	public NewThreadInfo(String title, int categoryId, String comment) {
		this.title = title;
		this.categoryId = categoryId;
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getComment() {
		return comment;
	}
	
}