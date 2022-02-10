/*
	処理内容:	カテゴリー一覧用PreparedStatement設定クラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryList extends CategoryDAO {

	public ArrayList<CategoryListInfo> findCategoryList(String searchName, String selectMatch) {	
		return super.findCategoryList(searchName, selectMatch);
	}
	
	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		return pstmt;
	}
	
}
