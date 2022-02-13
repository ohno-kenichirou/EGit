/*
	処理内容:	性別用のデータを保持するクラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.util.Objects;

public class GenderInfo implements Comparable<GenderInfo> {
	private int genderId; 
	private String genderName;
	
	public GenderInfo(int genderId, String genderName) {
		this.genderId = genderId;
		this.genderName = genderName;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenderInfo other = (GenderInfo) obj;
		return genderId == other.genderId;
	}

	@Override
	public String toString() {
		return  getGenderName();
	}

	@Override
	public int compareTo(GenderInfo g) {
		return Integer.compare(this.getGenderId(), g.getGenderId());
	}
	
}
