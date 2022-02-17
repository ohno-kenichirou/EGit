package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class UserDAO {

	private String sqlserver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String connection = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
	
	public String getSqlserver() {
		return sqlserver;
	}
	
	public String getConnection() {
		return connection;
	}
	
	public int findUser(TryUserLogin tryLogin) {
		//ログインが可能かどうかを判断するメソッド		
		
		int check = 2;
		//戻り値が0ならログイン可能、1ならアカウントロック状態、2ならログイン不可		
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return check;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "DECLARE @HashThis NVARCHAR(32); "
						+ "SET @HashThis = CONVERT(NVARCHAR(32), ?); "
						+ "SELECT email, pass, errorCount "
						+ "FROM [User] "
						+ "WHERE email = ? AND pass = (SELECT HASHBYTES('SHA2_256', @HashThis))";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tryLogin.getPass());
			pstmt.setString(2, tryLogin.getEmail());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt("errorCount") >= 3) {
					check = 1;
				} else if (rs.getInt("errorCount") == 1 || rs.getInt("errorCount") == 2){
					sql = "UPDATE [User]"
						+ "SET errorCount = 0 "
						+ "WHERE email = ?";
					pstmt = con.prepareStatement(sql);	
					pstmt.setString(1, tryLogin.getEmail());
					pstmt.executeUpdate();
					check = 0;
				} else {
					check = 0;
				}
			} else {
				sql = "SELECT 1 "
					+ "FROM [User] "
					+ "WHERE email = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, tryLogin.getEmail());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					sql = "UPDATE [User]"
						+ "SET errorCount = (SELECT errorCount "
						+ "					 FROM [User] "
						+ " 				 WHERE email = ?) + 1"
						+ "WHERE email = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, tryLogin.getEmail());
					pstmt.setString(2, tryLogin.getEmail());
					pstmt.executeUpdate();
				}
			}
			
			rs.close();
			pstmt.close();
			
			return check;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return check;
		}
	}
	
	public UserInfo getUserInfo(TryUserLogin tryLogin) {
		//ユーザーのユーザーID、ユーザー名、管理者権限の情報を取得してUserInfoインスタンスを戻すメソッド

		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT userId, userName, manager "
					   + "FROM [User] "
					   + "WHERE email = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tryLogin.getEmail());
			
			ResultSet rs = pstmt.executeQuery();
			UserInfo userInfo = null;
			
			if (rs.next()) {
				userInfo = new UserInfo(rs.getString("userId"), rs.getString("userName"), rs.getInt("manager"));
			}
							
			rs.close();
			pstmt.close();
			
			return userInfo;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* 追加 2022/02/10(木)　大野賢一朗 start */
	public ArrayList<UserInfo> findUserInfoList(String searchName, String selectMatch, String pageNo) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			int pageNum = 1;
			if (pageNo != null && !pageNo.equals("")) {
				pageNum = Integer.parseInt(pageNo);
			}
			String sql = "SELECT userId, userName, email, birth, genderId, dispInsUserId, dispInsDate, dispUpdUserId, dispUpdDate, manager, errorCount "
					   + "FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY dispInsDate, userId) AS rowNum FROM [User] WHERE delFlg = 0 ";
			System.out.println(searchName);
			if (searchName != null && !searchName.equals("")) {
				String where = "LIKE";
				System.out.println(selectMatch);
				if (selectMatch.equals("partial")) {
					where = "LIKE";
				} else if (selectMatch.equals("perfect")) {
					where = "=";
				}
				sql += " AND userName " + where + " ? ";
				System.out.println(sql);
			}
			sql += ") AS t "
				+  "WHERE rowNum BETWEEN ? AND ? ";
			System.out.println(sql);
			PreparedStatement pstmt = con.prepareStatement(sql);
			int parameterIndex = 1;
			if (searchName != null && !searchName.equals("")) {
				String whereSearchName = "%" + searchName + "%";
				if (selectMatch.equals("partial")) {
					whereSearchName = "%" + searchName + "%";
				} else if (selectMatch.equals("perfect")) {
					whereSearchName = searchName;
				}
				System.out.println(whereSearchName);
				pstmt.setString(parameterIndex++, whereSearchName);
			}
			pstmt.setInt(parameterIndex++, pageNum * 10 - 9);
			pstmt.setInt(parameterIndex++, pageNum * 10);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<UserInfo> userList = new ArrayList<>();
			while (rs.next()) {
				userList.add(new UserInfo(	rs.getString("userId"),			rs.getString("userName"),	"",
											rs.getString("email"),			rs.getDate("birth"),		rs.getInt("genderId"),
											rs.getString("dispInsUserId"),	rs.getDate("dispInsDate"),	rs.getString("dispUpdUserId"),
											rs.getDate("dispUpdDate"),		rs.getInt("manager"),		rs.getInt("errorCount")
							));
			}
			rs.close();
			pstmt.close();
			Collections.sort(userList);
			System.out.println(userList);
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int findCntUserInfo(String searchName, String selectMatch) {
		int ret = 0;
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ret;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT COUNT(*) AS cnt "
					   + "FROM [User] "
					   + "WHERE delFlg = 0 ";
			if (searchName != null && !searchName.equals("")) {
				String where = "LIKE";
				if (selectMatch.equals("partial")) {
					where = "LIKE";
				} else if (selectMatch.equals("perfect")) {
					where = "=";
				}
				sql += " AND userName " + where + " ? ";
			}
			PreparedStatement pstmt = con.prepareStatement(sql);
			int parameterIndex = 1;
			if (searchName != null && !searchName.equals("")) {
				String whereSearchName = "%" + searchName + "%";
				if (selectMatch.equals("partial")) {
					whereSearchName = "%" + searchName + "%";
				} else if (selectMatch.equals("perfect")) {
					whereSearchName = searchName;
				}
				pstmt.setString(parameterIndex++, whereSearchName);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("cnt");
			}
			rs.close();
			pstmt.close();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return ret;
		}
	}
	
	public ArrayList<UserInfo> getAllUserList() {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT userId, userName, email, birth, genderId, dispInsUserId, dispInsDate, dispUpdUserId, dispUpdDate, manager, errorCount "
					   + "FROM [User] ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<UserInfo> userList = new ArrayList<>();
			while (rs.next()) {
				userList.add(new UserInfo(	rs.getString("userId"),			rs.getString("userName"),	"",
											rs.getString("email"),			rs.getDate("birth"),		rs.getInt("genderId"),
											rs.getString("dispInsUserId"),	rs.getDate("dispInsDate"),	rs.getString("dispUpdUserId"),
											rs.getDate("dispUpdDate"),		rs.getInt("manager"),		rs.getInt("errorCount")
							));
			}
			rs.close();
			pstmt.close();
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean delAccount(UserInfo user, UserInfo account) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		Connection con = null;
		boolean ret = false;
		try {
			con = DriverManager.getConnection(this.getConnection());
			con.setAutoCommit(false);
			// ユーザーテーブル更新
			String sql = "UPDATE	[User] "
					   + "SET		delFlg = 1 "
					   + "	,		dispUpdUserId = ? "
					   + "	,		dispUpdDate = GETDATE() "
					   + "	,		updUserId = ? "
					   + "	,		updDate = GETDATE() "
					   + "WHERE		delFlg = 0 "
					   + "	AND		userId = ? ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserId());
			pstmt.setString(3, account.getUserId());
			ret = pstmt.executeUpdate() > 0;
			System.out.println("1:" + ret);		// テストコメント
			if (ret) {
				ret= delThread(pstmt, con, user, account);
			}
						
			if (ret) {
				con.commit();
				System.out.println("commit");		// テストコメント
			} else {
				con.rollback();
				System.out.println("rollback");		// テストコメント
			}
			pstmt.close();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				con.rollback();
				ret = false;
			} catch (SQLException e2) {
				e2.printStackTrace();
				ret = false;
			} 
		} finally {
			try {
				if (con != null) {
					con.close();
				}	
			} catch (SQLException e3) {
				e3.printStackTrace();
				ret = false;
			}
		}
		return ret;
	}
	
	public boolean delThread(PreparedStatement pstmt, Connection con, UserInfo user, UserInfo account) throws SQLException {
		// スレッドテーブル更新
		ThreadDAO threadDao = new ThreadDAO();
		String sql = threadDao.findThreadIdListSql();
		sql += threadDao.ByMakeUserIdSql();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, account.getUserId());
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Integer> threadIdList = new ArrayList<>();
		while (rs.next()) {
			threadIdList.add(rs.getInt("threadId"));
		}
		boolean ret = true;
		if (threadIdList.size() > 0) {
			sql = threadDao.deleteThreadSql();		 
			sql += threadDao.ByMakeUserIdSql();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, account.getUserId());
			ret = pstmt.executeUpdate() > 0;
		}
		if (rs != null) {
			rs.close();
		}
		if (ret) {
			ret = delCommentByThreadId(pstmt, con, user, account, threadIdList);
		}
		System.out.println("2:" + ret);		// テストコメント
		return ret;
	}
	
	public boolean delCommentByThreadId(PreparedStatement pstmt, Connection con, UserInfo user, UserInfo account, ArrayList<Integer> threadIdList) throws SQLException {
		// コメントテーブル(スレッドID)更新
		boolean ret = true; 
		CommentDAO commentDao = new CommentDAO();
		ArrayList<Integer> commentIdList = new ArrayList<>();
		ResultSet rs = null;
		if (threadIdList.size() > 0) {
			for (Integer threadId : threadIdList) {
				String sql = commentDao.findCommentIdListSql();
				sql += commentDao.ByThreadIdSql();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, threadId);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					commentIdList.add(rs.getInt("commentId"));
				}
				if (commentIdList.size() > 0) {
					sql = commentDao.deleteCommentSql();		 
					sql += commentDao.ByThreadIdSql();
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, user.getUserId());
					pstmt.setInt(2, threadId);
					ret = pstmt.executeUpdate() > 0;
					if (!ret) {
						break;
					}
				}
			}
		}
		if (rs != null) {
			rs.close();
		}
		if (ret) {
			ret = delCommentByPostUserId(pstmt, con, user, account, commentDao);
		}
		System.out.println("3:" + ret);		// テストコメント
		return ret;
	}
	
	public boolean delCommentByPostUserId(PreparedStatement pstmt, Connection con, UserInfo user, UserInfo account, CommentDAO commentDao) throws SQLException {
		// コメントテーブル(投稿ユーザーID)更新
		boolean ret = true; 
		String sql = commentDao.findCommentIdListSql();
		sql += commentDao.ByPostUserIdSql();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, account.getUserId());
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Integer> commentIdList = new ArrayList<>();
		while (rs.next()) {
			commentIdList.add(rs.getInt("commentId"));
		}
		if (commentIdList.size() > 0) {
			sql = commentDao.deleteCommentSql();		 
			sql += commentDao.ByPostUserIdSql();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, account.getUserId());
			ret = pstmt.executeUpdate() > 0;
		}
		if (rs != null) {
			rs.close();
		}
		System.out.println("4:" + ret);		// テストコメント
		return ret;
	}
	
	public boolean modifyAccount(UserInfo user, UserInfo account, String lift) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try(Connection con = DriverManager.getConnection(this.getConnection())) {
			String sql = "UPDATE [User] "
					   + "SET userName = ?      ,pass = HASHBYTES('SHA2_256',CONVERT(NVARCHAR(32),?)) ,email = ?   ,birth = ?     ,genderId = ? "
					   + "	, dispUpdUserId = ? ,dispUpdDate = GETDATE()                              ,manager = ? ,updUserId = ? ,updDate = GETDATE() ";
			if (account.getErrorCount() >= 3 && lift.equals("1")) {
				sql += " , errorCount = 0 ";
			}
			sql += "WHERE userId = ? ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getUserName());
			pstmt.setString(2, account.getPass());
			pstmt.setString(3, account.getEmail());
			pstmt.setDate(4, account.getBirth());
			pstmt.setInt(5, account.getGenderId());
			pstmt.setString(6, user.getUserId());
			pstmt.setInt(7, account.getManager());
			pstmt.setString(8, user.getUserId());
			pstmt.setString(9, account.getUserId());
			boolean ret = pstmt.executeUpdate() > 0;
			pstmt.close();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 		
	}
	
	public boolean registerAccount(UserInfo user, UserInfo account) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try(Connection con = DriverManager.getConnection(this.getConnection())) {
			String sql = "INSERT INTO [User] "
					   + "( userId        ,userName    ,pass    ,email     ,birth   ,genderId "
					   + ", dispInsUserId ,dispInsDate ,manager ,insUserId ,insDate "
					   + ") VALUES "
					   + "( ? ,? ,HASHBYTES('SHA2_256',CONVERT(NVARCHAR(32),?)) ,? ,? ,? "
					   + ", ? ,GETDATE() ,? ,? ,GETDATE())";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getUserId());
			pstmt.setString(2, account.getUserName());
			pstmt.setString(3, account.getPass());
			pstmt.setString(4, account.getEmail());
			pstmt.setDate(5, account.getBirth());
			pstmt.setInt(6, account.getGenderId());
			pstmt.setString(7, user.getUserId());
			pstmt.setInt(8, account.getManager());
			pstmt.setString(9, user.getUserId());
			boolean ret = pstmt.executeUpdate() > 0;
			pstmt.close();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 		
	}
	/* 追加 2022/02/10(木)　大野賢一朗 end */
	
}
