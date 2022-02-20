/*
	処理内容:	カテゴリー一覧用DAO
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class CategoryListDAO extends CategoryCommonDAO {

	public ArrayList<CategoryInfo> findCategoryList() {	
		return super.findCategoryList("", "");
	}
	
	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		return pstmt;
	}
		
	public ArrayList<CategoryNameDisp> searchAndSetList() {
		//カテゴリーリストの作成メソッド		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser");) {
		
			String sql = "SELECT categoryId, categoryName, categoryKana "
					   + "FROM Category "
					   + "WHERE delFlg = 0 "
					   + "ORDER BY categoryKana";
					   
			PreparedStatement pstmt = con.prepareStatement(sql);
						
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<CategoryNameDisp> categoryList = new ArrayList<>();
			
			while (rs.next()) {
				categoryList.add(new CategoryNameDisp(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryKana")));
			}
			
			rs.close();
			pstmt.close();
			
			return categoryList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int categoryDispNumber(int page) {
		return ((page - 1) * 10);
	}
	
	public int paginationCount(int count) {
		return (int)Math.ceil((double)count / 10);
	}
	
	public Integer searchAndSetListCount() {
		//カテゴリー一覧ページネーション数作成メソッド		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser");) {
			
			String sql = "SELECT COUNT(categoryId) AS count "
					   + "FROM Category "
					   + "WHERE delFlg = 0";					   
					   
			PreparedStatement pstmt = con.prepareStatement(sql);
						
			ResultSet rs = pstmt.executeQuery();
			
			Integer count;
			if (rs.next()) {
				if (rs.getInt("count") >= 1) {
					count = this.paginationCount(rs.getInt("count"));
				} else {
					count = null;
				}				
			} else {
				count = null;
			}
			
			rs.close();
			pstmt.close();
			
			return count;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList<CategoryNameDisp> searchAndSetList(int page) {
		//カテゴリー一覧ページ用リストの作成メソッド		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser");) {
			
			int categoryDispNumber =  this.categoryDispNumber(page);
			
			String sql = "SELECT categoryId, categoryName, categoryKana "
					   + "FROM Category "
					   + "WHERE delFlg = 0 "
					   + "ORDER BY categoryKana "
					   + "offset ? rows "
					   + "fetch next 10 rows only";
					   
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, categoryDispNumber);
						
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<CategoryNameDisp> categoryList = new ArrayList<>();
			
			while (rs.next()) {
				categoryList.add(new CategoryNameDisp(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryKana")));
			}
			
			rs.close();
			pstmt.close();
			
			return categoryList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList<CategoryNameDisp> searchCategory(CategorySearchInfo categorySearch, int page) {
		//カテゴリー検索のメソッド		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser");) {
			
			int categoryDispNumber =  this.categoryDispNumber(page);
			
			String sql = "SELECT categoryId, categoryName, categoryKana "
					   + "FROM Category ";
			
			PreparedStatement pstmt;
			if (categorySearch.getMatch().equals("part")) {
				sql = sql.concat("WHERE (categoryName LIKE ? OR categoryKana LIKE ?) AND delFlg = 0 ORDER BY categoryKana offset ? rows fetch next 10 rows only");
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + categorySearch.getSearchWord() + "%");
				pstmt.setString(2, "%" + categorySearch.getSearchWord() + "%");
				pstmt.setInt(3, categoryDispNumber);
			} else {
				sql = sql.concat("WHERE (categoryName = ? OR categoryKana = ?) AND delFlg = 0 ORDER BY categoryKana offset ? rows fetch next 10 rows only");
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, categorySearch.getSearchWord());
				pstmt.setString(2, categorySearch.getSearchWord());
				pstmt.setInt(3, categoryDispNumber);
			}
				
			ResultSet rs = pstmt.executeQuery();
			ArrayList<CategoryNameDisp> categoryList = new ArrayList<>();
			
			while (rs.next()) {
				categoryList.add(new CategoryNameDisp(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryKana")));
			}
			Collections.sort(categoryList);
			
			rs.close();
			pstmt.close();
			
			return categoryList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer searchCategoryCount(CategorySearchInfo categorySearch) {
		//カテゴリー検索のページネーション作成メソッド		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser");) {
			
			String sql = "SELECT COUNT(categoryId) AS count "
					   + "FROM Category ";
			
			PreparedStatement pstmt;
			if (categorySearch.getMatch().equals("part")) {
				sql = sql.concat("WHERE (categoryName LIKE ? OR categoryKana LIKE ?) AND delFlg = 0");
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + categorySearch.getSearchWord() + "%");
				pstmt.setString(2, "%" + categorySearch.getSearchWord() + "%");
			} else {
				sql = sql.concat("WHERE (categoryName = ? OR categoryKana = ?) AND delFlg = 0");
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, categorySearch.getSearchWord());
				pstmt.setString(2, categorySearch.getSearchWord());
			}
				   		
			ResultSet rs = pstmt.executeQuery();
			Integer count;
			if (rs.next()) {
				if (rs.getInt("count") >= 1) {
					count = this.paginationCount(rs.getInt("count"));
				} else {
					count = null;
				}				
			} else {
				count = null;
			}
			
			rs.close();
			pstmt.close();
			
			return count;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
