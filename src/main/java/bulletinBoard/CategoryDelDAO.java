/*
	処理内容:	カテゴリー削除用DAO
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDelDAO extends CategoryCommonDAO {
	private CategoryInfo category;
	
	public CategoryDelDAO(UserInfo user, CategoryInfo category) {
		super(user);
		this.category = category;
	}

	public CategoryInfo getCategory() {
		return category;
	}

	public void setCategory(CategoryInfo category) {
		this.category = category;
	}

	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, this.getUser().getUserId());
		pstmt.setInt(2, this.getCategory().getCategoryId());
		return pstmt;
	}
	
}
