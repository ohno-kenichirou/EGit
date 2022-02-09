package bulletinBoard;

public class DeleteThreadInfo {
	private int threadId;
	private String title;
	private String category;
	private String makeUserName;
	private String makeDate;
	private String comment;
	
	public DeleteThreadInfo(int threadId, String title, String category, String makeUserName, String makeDate,
			String comment) {
		this.threadId = threadId;
		this.title = title;
		this.category = category;
		this.makeUserName = makeUserName;
		this.makeDate = makeDate;
		this.comment = comment;
	}

	public int getThreadId() {
		return threadId;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getMakeUserName() {
		return makeUserName;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public String getComment() {
		return comment;
	}
	
}
