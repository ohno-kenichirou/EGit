package bulletinBoard;

import java.sql.Date;
import java.util.Objects;

public class UserInfo implements Comparable<UserInfo> {
	private String userId;
	private String userName;
	private String pass;
	private String email;
	private Date birth;
	private int genderId;
	private String dispInsUserId;
	private Date dispInsDate;
	private String dispUpdUserId;
	private Date dispUpdDate;
	private int manager;
	private int errorCount;
		
	public UserInfo(String userId,			String userName,	String pass,			String email,		Date birth,		int genderId,
					String dispInsUserId,	Date dispInsDate,	String dispUpdUserId,	Date dispUpdDate,	int manager,	int errorCount) {
		this.userId = userId;
		this.userName = userName;
		this.manager = manager;
		this.pass = pass;
		this.email = email;
		this.birth = birth;
		this.genderId = genderId;
		this.dispInsUserId = dispInsUserId;
		this.dispInsDate = dispInsDate;
		this.dispUpdUserId = dispUpdUserId;
		this.dispUpdDate = dispUpdDate;
		this.errorCount = errorCount;
	}

	public UserInfo(String userId, String userName, int manager) {
		this(userId, userName, "", "", null, 0, "", null, null, null, manager, 0);
	}
	
	public UserInfo() {
		this("", "", 0);
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

	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getGenderId() {
		return genderId;
	}
	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public String getDispInsUserId() {
		return dispInsUserId;
	}
	public void setDispInsUserId(String dispInsUserId) {
		this.dispInsUserId = dispInsUserId;
	}

	public Date getDispInsDate() {
		return dispInsDate;
	}
	public void setDispInsDate(Date dispInsDate) {
		this.dispInsDate = dispInsDate;
	}

	public String getDispUpdUserId() {
		return dispUpdUserId;
	}
	public void setDispUpdUserId(String dispUpdUserId) {
		this.dispUpdUserId = dispUpdUserId;
	}

	public Date getDispUpdDate() {
		return dispUpdDate;
	}
	public void setDispUpdDate(Date dispUpdDate) {
		this.dispUpdDate = dispUpdDate;
	}

	public int getManager() {
		return manager;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}

	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		return Objects.equals(userId, other.userId);
	}

	@Override
	public int compareTo(UserInfo u) {
		int ret = this.getDispInsDate().compareTo(u.getDispInsDate());
		if (ret != 0) {
			return ret;
		} else {
			return this.getUserId().compareTo(u.getUserId());
		}
	}
		
}