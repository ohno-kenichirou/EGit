/*
	処理内容:	カテゴリー共通DAO
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
*/
package bulletinBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class CategoryCommonDAO extends ProcDAO {
	private UserInfo user;
	
	public CategoryCommonDAO(UserInfo user) {
		this.user = user;
	}
	
	public CategoryCommonDAO() {
		this(null);
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public boolean addCategory() {
		String sql =	"INSERT INTO Category " +
						"(categoryName ,categoryKana ,regsterUserId ,makeDate ,insUserId ,insDate) " +
						"VALUES (? ,? ,? ,GETDATE() ,? ,GETDATE())";
		return procUpd(sql);
	}
	
	public boolean modifyCategory() {
		String sql =	"UPDATE	Category " +
						"SET	categoryName = ? " +
						"  ,	categoryKana = ? " +
						"  ,	updUserId = ? " +
						"  ,	updDate = GETDATE() " +
						"WHERE	categoryId = ? ";
		return procUpd(sql);
	}
	
	public boolean delCategory() {
		String sql =	"UPDATE	Category " +
						"SET	delFlg = 1 " +
						"  ,	updUserId = ? " +
						"  ,	updDate = GETDATE() "+
						"WHERE	categoryId = ? ";
		return procUpd(sql);
	}
	
	public ArrayList<CategoryListInfo> findCategoryList(String searchName, String selectMatch) {
		try (Connection con = connectDb()) {
			String sql =	"SELECT	categoryId			AS id " +
							"	  , categoryName		AS name " +
							"	  , categoryKana		AS kana " +
							"FROM	Category " +
							"WHERE	delFlg = 0 ";
			
			if (searchName != null && !searchName.equals("")) {
				String where = "LIKE";
				if (selectMatch.equals("partial")) {
					where = "LIKE";
				} else if (selectMatch.equals("perfect")) {
					where = "=";
				}
				sql += " AND categoryName " + where + " ? ";
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			if (searchName != null && !searchName.equals("")) {
				String whereSearchName = "%" + searchName + "%";
				if (selectMatch.equals("partial")) {
					whereSearchName = "%" + searchName + "%";
				} else if (selectMatch.equals("perfect")) {
					whereSearchName = searchName;
				}
				pstmt.setString(1, whereSearchName);
			}
			
			ResultSet rs = pstmt.executeQuery();
			ArrayList<CategoryListInfo> resultCategoryList = new ArrayList<>();
			while (rs.next()) {
				resultCategoryList.add(new CategoryListInfo(rs.getInt("id"), rs.getString("name"), rs.getString("kana")));
			}
			
			rs.close();
			pstmt.close();
			return resultCategoryList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
