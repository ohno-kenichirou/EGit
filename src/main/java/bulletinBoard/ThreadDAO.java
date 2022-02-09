package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class ThreadDAO {
	private String sqlserver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String connection = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
	
	public String getSqlserver() {
		return sqlserver;
	}
	
	public String getConnection() {
		return connection;
	}
	
	public ArrayList<ThreadDispInfo> searchAndSetList(int page) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			int threadDispNumber =  ((page - 1) * 10) + 1;
			
			String sql = "SELECT threadId, title, categoryName AS category, [User].userName AS createUserName, Thread.makeDate AS createDate, content AS comment "
					   + "FROM Thread INNER JOIN Category ON Thread.categoryId = Category.categoryId "
					   + "INNER JOIN [User] ON Thread.makeUserId = [User].userId "
					   + "WHERE ThreadId >= ? AND ThreadId < ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadDispNumber);
			pstmt.setInt(2, threadDispNumber + 10);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ThreadDispInfo> threadList = new ArrayList<>();
			
			while (rs.next()) {
				threadList.add(new ThreadDispInfo(rs.getInt("threadId"), rs.getString("title"), rs.getString("category"), rs.getString("createUserName"), rs.getString("createDate"), rs.getString("comment")));
			}
			Collections.sort(threadList);
			
			rs.close();
			pstmt.close();
			
			return threadList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
