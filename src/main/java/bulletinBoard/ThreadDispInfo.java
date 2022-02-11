package bulletinBoard;

public class ThreadDispInfo implements Comparable<ThreadDispInfo>{

	private int threadId;
	private String title;
	private String category;
	private String createUserName;
	private String createDate;
	private String comment;
	
	public ThreadDispInfo(int threadId, String title, String category, String createUserName, String createDate, String comment) {
		this.threadId = threadId;
		this.title = title;
		this.category = category;
		this.createUserName = createUserName;
		this.createDate = createDate;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getComment() {
		return comment;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((createUserName == null) ? 0 : createUserName.hashCode());
		result = prime * result + threadId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ThreadDispInfo other = (ThreadDispInfo) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUserName == null) {
			if (other.createUserName != null)
				return false;
		} else if (!createUserName.equals(other.createUserName))
			return false;
		if (threadId != other.threadId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(ThreadDispInfo t) {
		return Integer.compare(t.getThreadId(), this.getThreadId());
	}
}
