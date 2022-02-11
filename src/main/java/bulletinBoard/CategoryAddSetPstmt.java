/*
	処理内容:	カテゴリー追加用PreparedStatement設定クラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryAddSetPstmt extends CategoryDAO {
	private CategoryAddInfo category;
	
	public CategoryAddSetPstmt(UserInfo user, CategoryAddInfo category) {
		super(user);
		this.category = category;
	}

	public CategoryAddInfo getCategory() {
		return category;
	}

	public void setCategory(CategoryAddInfo category) {
		this.category = category;
	}

	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, this.getCategory().getCategoryName());
		pstmt.setString(2, this.getCategory().getCategoryKana());
		pstmt.setString(3, this.getUser().getUserId());
		pstmt.setString(4, this.getUser().getUserId());
		return pstmt;
	}
}
