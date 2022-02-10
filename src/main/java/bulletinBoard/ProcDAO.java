/*
	処理内容:	データベース接続設定
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
*/
package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ProcDAO {

	public Connection connectDb() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
		return DriverManager.getConnection(connectionUrl);
	}
	
	public boolean procUpd(String sql) {
		try (Connection con = connectDb()) {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = setPstmt(pstmt);
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
	
	public abstract PreparedStatement setPstmt(PreparedStatement pstmt) throws SQLException;

}
