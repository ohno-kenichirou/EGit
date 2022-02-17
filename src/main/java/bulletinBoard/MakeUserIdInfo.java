package bulletinBoard;

import java.sql.Date;

public class MakeUserIdInfo {
	private Date thisDate;
	private String idAlphabet;
	private int idNo;
	
	public MakeUserIdInfo(Date thisDate, String idAlphabet, int idNo) {
		this.thisDate = thisDate;
		this.idAlphabet = idAlphabet;
		this.idNo = idNo;
	}

	public Date getThisDate() {
		return thisDate;
	}

	public void setThisDate(Date thisDate) {
		this.thisDate = thisDate;
	}

	public String getIdAlphabet() {
		return idAlphabet;
	}

	public void setIdAlphabet(String idAlphabet) {
		this.idAlphabet = idAlphabet;
	}

	public int getIdNo() {
		return idNo;
	}

	public void setIdNo(int idNo) {
		this.idNo = idNo;
	}
	
}
