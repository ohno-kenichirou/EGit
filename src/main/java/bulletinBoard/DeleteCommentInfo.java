package bulletinBoard;

public class DeleteCommentInfo {

	private int threadId;
	private int commentId;
	private String postUserName;
	private String postDate;
	private String comment;
	
	public DeleteCommentInfo(int threadId, int commentId, String postUserName, String postDate, String comment) {
		this.threadId = threadId;
		this.commentId = commentId;
		this.postUserName = postUserName;
		this.postDate = postDate;
		this.comment = comment;
	}

	public int getThreadId() {
		return threadId;
	}
	
	public int getCommentId() {
		return commentId;
	}

	public String getPostUserName() {
		return postUserName;
	}

	public String getPostDate() {
		return postDate;
	}

	public String getComment() {
		return comment;
	}

}
