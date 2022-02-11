package bulletinBoard;

public class CommentInfo implements Comparable<CommentInfo>{
	private int commentId;
	private String postUserName;
	private String postDate;
	private String comment;
	
	public CommentInfo(int commentId, String postUserName, String postDate, String comment) {
		this.commentId = commentId;
		this.postUserName = postUserName;
		this.postDate = postDate;
		this.comment = comment;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + commentId;
		result = prime * result + ((postDate == null) ? 0 : postDate.hashCode());
		result = prime * result + ((postUserName == null) ? 0 : postUserName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentInfo other = (CommentInfo) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (commentId != other.commentId)
			return false;
		if (postDate == null) {
			if (other.postDate != null)
				return false;
		} else if (!postDate.equals(other.postDate))
			return false;
		if (postUserName == null) {
			if (other.postUserName != null)
				return false;
		} else if (!postUserName.equals(other.postUserName))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(CommentInfo c) {
		return Integer.compare(this.getCommentId(), c.getCommentId());
	}
	
}
