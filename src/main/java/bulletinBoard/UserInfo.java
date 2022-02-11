package bulletinBoard;

public class UserInfo {
	private String userId;
	private String userName;
	private int manager;
	
	public UserInfo(String userId, String userName, int manager) {
		this.userId = userId;
		this.userName = userName;
		this.manager = manager;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}
		
}
