/*
	処理内容:	カテゴリー一覧用DAO
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryListDAO extends CategoryCommonDAO {

	public ArrayList<CategoryInfo> findCategoryList() {	
		return super.findCategoryList("", "");
	}
	
	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		return pstmt;
	}
	
}
