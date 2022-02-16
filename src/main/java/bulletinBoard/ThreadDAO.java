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
	
	public int threadDispNumber(int page) {
		return ((page - 1) * 10);
	}
	
	public int paginationCount(int count) {
		return (int)Math.ceil((double)count / 10);
	}
	
	public Integer searchAndSetListCount() {
		//スレッド一覧用のページネーション数作成メソッド
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "SELECT COUNT(threadId) AS count "
					   + "FROM Thread "
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
		
	public ArrayList<ThreadDispInfo> searchAndSetList(int page) {
		//スレッド一覧用のリスト作成メソッド
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			int threadDispNumber =  this.threadDispNumber(page);
			
			String sql = "SELECT threadId, title, categoryName AS category, [User].userName AS createUserName, Thread.makeDate AS createDate, content AS comment "
					   + "FROM Thread INNER JOIN Category ON Thread.categoryId = Category.categoryId "
					   + "INNER JOIN [User] ON Thread.makeUserId = [User].userId "
					   + "WHERE Thread.delFlg = 0 "
					   + "ORDER BY createDate DESC "
					   + "offset ? rows "
					   + "fetch next 10 rows only";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadDispNumber);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ThreadDispInfo> threadList = new ArrayList<>();
			
			while (rs.next()) {
				threadList.add(new ThreadDispInfo(rs.getInt("threadId"), rs.getString("title"), rs.getString("category"), rs.getString("createUserName"), rs.getString("createDate").substring(0, 19), rs.getString("comment")));
			}
			
			rs.close();
			pstmt.close();
			
			return threadList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public ThreadDispInfo threadDisp(int threadId) {
		//スレッド詳細画面の情報取得メソッド
		
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
		//新規スレッド作成のメソッド
		
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
		//ThreadテーブルとCommentテーブルのthreadIdを論理削除するメソッド
		
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
	
	public ArrayList<ThreadDispInfo> threadSearch(ThreadSearchInfo threadSearch, int page) {
		//スレッド検索のメソッド
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
				
		try (Connection con = DriverManager.getConnection(this.getConnection());){
			
			int threadDispNumber =  this.threadDispNumber(page);
			
			String sql = "SELECT threadId, title, categoryName AS category, [User].userName AS createUserName, Thread.makeDate AS createDate, content AS comment "
					   + "FROM Thread INNER JOIN Category ON Thread.categoryId = Category.categoryId "
					   + "INNER JOIN [User] ON Thread.makeUserId = [User].userId ";
			
			PreparedStatement pstmt;
			if (threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() == 0) {
				sql = sql.concat("WHERE Thread.delFlg = 0 ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, threadDispNumber);
			} else if (!threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() == 0) {
				if (threadSearch.getMatch().equals("part")) {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title LIKE ? ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%" + threadSearch.getSearchWord() + "%");
				} else {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title = ? ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, threadSearch.getSearchWord());
				}
				pstmt.setInt(2, threadDispNumber);
			} else if (threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() >= 1) {
				sql = sql.concat("WHERE Thread.delFlg = 0 AND Thread.categoryId = ? ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, threadSearch.getCategoryId());
				pstmt.setInt(2, threadDispNumber);
			} else {
				if (threadSearch.getMatch().equals("part")) {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title LIKE ? AND Thread.categoryId = ? ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%" + threadSearch.getSearchWord() + "%");
				} else {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title = ? AND Thread.categoryId = ? ORDER BY createDate DESC offset ? rows fetch next 10 rows only");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, threadSearch.getSearchWord());
				}
				pstmt.setInt(2, threadSearch.getCategoryId());
				pstmt.setInt(3, threadDispNumber);
							
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ThreadDispInfo> threadList = new ArrayList<>();
			
			while (rs.next()) {
				threadList.add(new ThreadDispInfo(rs.getInt("threadId"), rs.getString("title"), rs.getString("category"), rs.getString("createUserName"), rs.getString("createDate").substring(0, 19), rs.getString("comment")));
			}
			
			rs.close();
			pstmt.close();
			
			return threadList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Integer threadSearchCount(ThreadSearchInfo threadSearch) {
		//スレッド検索のページネーション数作成メソッド
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
				
		try (Connection con = DriverManager.getConnection(this.getConnection());){
			
			String sql = "SELECT COUNT(Thread.threadId) AS count "
					   + "FROM Thread INNER JOIN Category ON Thread.categoryId = Category.categoryId "
					   + "INNER JOIN [User] ON Thread.makeUserId = [User].userId ";
			
			PreparedStatement pstmt;
			if (threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() == 0) {
				sql = sql.concat("WHERE Thread.delFlg = 0");
				pstmt = con.prepareStatement(sql);				
			} else if (!threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() == 0) {
				if (threadSearch.getMatch().equals("part")) {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title LIKE ?");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%" + threadSearch.getSearchWord() + "%");
				} else {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title = ?");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, threadSearch.getSearchWord());
				}
			} else if (threadSearch.getSearchWord().equals("") && threadSearch.getCategoryId() >= 1) {
				sql = sql.concat("WHERE Thread.delFlg = 0 AND Thread.categoryId = ?");
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, threadSearch.getCategoryId());
			} else {
				if (threadSearch.getMatch().equals("part")) {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title LIKE ? AND Thread.categoryId = ?");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%" + threadSearch.getSearchWord() + "%");
				} else {
					sql = sql.concat("WHERE Thread.delFlg = 0 AND title = ? AND Thread.categoryId = ?");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, threadSearch.getSearchWord());
				}
				pstmt.setInt(2, threadSearch.getCategoryId());
							
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
	
	/* 追加 2022/02/10(木)　大野賢一朗 start */
	public String findThreadIdListSql() {
		return "SELECT	threadId "
			 + "FROM	Thread "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String deleteThreadSql() {
		return "UPDATE	Thread "
			 + "SET		delFlg = 1 "
			 + "  ,		updUserId = ? "
			 + "  ,		updDate = GETDATE() "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String ByMakeUserIdSql() {
		return "     	makeUserId = ? ";
	}
	/* 追加 2022/02/10(木)　大野賢一朗 end */
	
	
}
