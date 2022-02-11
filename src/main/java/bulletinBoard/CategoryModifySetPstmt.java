/*
	処理内容:	カテゴリー修正用PreparedStatement設定クラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryModifySetPstmt extends CategoryDAO {
	private CategoryModifyInfo category;
	
	public CategoryModifySetPstmt(UserInfo user, CategoryModifyInfo category) {
		super(user);
		this.category = category;
	}

	public CategoryModifyInfo getCategory() {
		return category;
	}

	public void setCategory(CategoryModifyInfo category) {
		this.category = category;
	}

	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, this.getCategory().getCategoryName());
		pstmt.setString(2, this.getCategory().getCategoryKana());
		pstmt.setString(3, this.getUser().getUserId());
		pstmt.setInt(4, this.getCategory().getCategoryId());
		return pstmt;
	}

}
