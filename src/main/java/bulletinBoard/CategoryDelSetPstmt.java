/*
	処理内容:	カテゴリー削除用PreparedStatement設定クラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDel extends CategoryDAO {
	private CategoryDelInfo category;
	
	public CategoryDel(UserInfo user, CategoryDelInfo category) {
		super(user);
		this.category = category;
	}

	public CategoryDelInfo getCategory() {
		return category;
	}

	public void setCategory(CategoryDelInfo category) {
		this.category = category;
	}

	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		pstmt.setInt(1, this.getCategory().getCategoryId());
		return pstmt;
	}
	
}
