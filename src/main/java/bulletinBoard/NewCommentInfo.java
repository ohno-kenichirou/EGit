package bulletinBoard;

public class NewCommentInfo {
	
	private int threadId;
	private String comment;
	
	public NewCommentInfo(int threadId, String comment) {
		this.threadId = threadId;
		this.comment = comment;
	}

	public int getThreadId() {
		return threadId;
	}

	public String getComment() {
		return comment;
	}
	
}
