package bulletinBoard;

public class NewCommentInfo {
	
	private int threadId;
	private String UserName;
	private String comment;
	
	public NewCommentInfo(int threadId, String userName, String comment) {
		this.threadId = threadId;
		UserName = userName;
		this.comment = comment;
	}

	public int getThreadId() {
		return threadId;
	}

	public String getUserName() {
		return UserName;
	}

	public String getComment() {
		return comment;
	}
	
}
