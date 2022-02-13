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
					   + "WHERE ThreadId >= ? AND ThreadId < ? AND Thread.delFlg = 0";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadDispNumber);
			pstmt.setInt(2, threadDispNumber + 10);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ThreadDispInfo> threadList = new ArrayList<>();
			
			while (rs.next()) {
				threadList.add(new ThreadDispInfo(rs.getInt("threadId"), rs.getString("title"), rs.getString("category"), rs.getString("createUserName"), rs.getString("createDate").substring(0, 19), rs.getString("comment")));
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
	
	public ThreadDispInfo threadDisp(int threadId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "SELECT threadId, title, categoryName AS category, [User].userName AS createUserName, Thread.makeDate AS createDate, content AS comment "
					   + "FROM Thread INNER JOIN Category ON Thread.categoryId = Category.categoryId "
					   + "INNER JOIN [User] ON Thread.makeUserId = [User].userId "
					   + "WHERE ThreadId = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			
			ResultSet rs = pstmt.executeQuery();
			
			ThreadDispInfo thread;
			
			if (rs.next()) {
				thread = new ThreadDispInfo(rs.getInt("threadId"), rs.getString("title"), rs.getString("category"), rs.getString("createUserName"), rs.getString("createDate").substring(0, 19), rs.getString("comment"));
			} else {
				thread = null;
			}
			
			rs.close();
			pstmt.close();
			
			return thread;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean newThread(NewThreadInfo newThread, UserInfo user) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "INSERT INTO Thread(title, categoryId, makeUserId, makeDate, content, delFlg, insUserId, insDate) "
					   + "VALUES (?, ?, ?, (SELECT CURRENT_TIMESTAMP), ?, 0, ?, (SELECT CURRENT_TIMESTAMP))";
					 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newThread.getTitle());
			pstmt.setInt(2, newThread.getCategoryId());
			pstmt.setString(3, user.getUserId());
			pstmt.setString(4, newThread.getComment());
			pstmt.setString(5, user.getUserId());
			
			int result = pstmt.executeUpdate();
			pstmt.close();
			
			if (result == 1) {
				return true;
			} else {
				return false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteThread(int threadId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		Connection con = null;
				
		try {
			
			con = DriverManager.getConnection(this.getConnection());
			con.setAutoCommit(false);
			
			String sql = "UPDATE Thread "
					   + "SET delFlg = 1 "
					   + "WHERE threadId = ?";   
					 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			int resultThread = pstmt.executeUpdate();
			
			sql = "UPDATE Comment "
				+ "SET delFlg = 1 "
			    + "WHERE threadId = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			int resultComment = pstmt.executeUpdate();
			
			con.commit();
			pstmt.close();
				
		} catch (SQLException e) {
			
			try {
				con.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			} 
			
		} finally {
			
			try {
				if (con != null) {
					con.close();
				}	
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			
		}
	}
	
	public void threadSearch(int threadId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		Connection con = null;
				
		try {
			
			con = DriverManager.getConnection(this.getConnection());
			con.setAutoCommit(false);
			
			String sql = "UPDATE Thread "
					   + "SET delFlg = 1 "
					   + "WHERE threadId = ?";   
					 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			int resultThread = pstmt.executeUpdate();
			
			sql = "UPDATE Comment "
				+ "SET delFlg = 1 "
			    + "WHERE threadId = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			int resultComment = pstmt.executeUpdate();
			
			con.commit();
			pstmt.close();
				
		} catch (SQLException e) {
			
			try {
				con.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			} 
			
		} finally {
			
			try {
				if (con != null) {
					con.close();
				}	
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			
		}
	}
	
	/* 追加 2022/02/10(木)　大野賢一朗 start */
	public String findThreadIdListSql() {
		return "SELECT	threadId "
			 + "FROM	Thread "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String deleteThreadSql() {
		return "UPDATE	Thread "
			 + "SET		delFlg = 1 "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String ByMakeUserIdSql() {
		return "     	makeUserId = ? ";
	}
	/* 追加 2022/02/10(木)　大野賢一朗 end */
	
}
