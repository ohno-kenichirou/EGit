package bulletinBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDAO extends ProcDAO {
	
	public boolean insCategory(UserInfo user, CategoryAddInfo category) {
		try (Connection con = conectDb()) {
			String sql = "";
			sql += "INSERT INTO Category ";
			sql += "(categoryName ,categoryKana ,regsterUserId ,makeDate ,insUserId ,insDate) ";
			sql += "VALUES (? ,? ,? ,GETDATE() ,? ,GETDATE())";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, category.getCategoryName());
			pstmt.setString(2, category.getCategoryKana());
			pstmt.setString(3, user.getUserId());
			pstmt.setString(4, user.getUserId());
			
			int rsRow = pstmt.executeUpdate();
			pstmt.close();
			return rsRow != 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
