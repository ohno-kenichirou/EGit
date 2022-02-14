package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		int check = 2;
		//戻り値が0ならログイン可能、1ならアカウントロック状態、2ならログイン不可		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return check;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT email, pass, errorCount "
					   + "FROM [User] "
					   + "WHERE email = ? AND pass = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tryLogin.getEmail());
			pstmt.setString(2, tryLogin.getPass());
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
	
	//ハッシュ化した文字列
	public String getHash(String pass) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT HASHBYTES('SHA2_256', ?) AS pass";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pass);
			ResultSet rs = pstmt.executeQuery();
			String passHash;
			if (rs.next()) {
				passHash =  rs.getString("pass");
			} else {
				passHash = null;
			}
			rs.close();
			pstmt.close();
			return passHash;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public void getHash(String pass) {
//		byte[] bytes;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			md.update(pass.getBytes());
//			bytes = md.digest();
//			StringBuilder sb = new StringBuilder(2 * bytes.length);
//			for (byte b : bytes) {
//				sb.append(String.format("%02x", b&0xff));
//			}
//			System.out.println(sb);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/* 追加 2022/02/10(木)　大野賢一朗 start */
	public ArrayList<UserInfo> getUserInfoList(String searchName, String selectMatch) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			String sql = "SELECT userId, userName, email, birth, genderId, dispInsUserId, dispInsDate, dispUpdUserId, dispUpdDate, manager, errorCount "
					   + "FROM [User] "
					   + "WHERE	delFlg = 0 ";
			
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
			ArrayList<UserInfo> userList = new ArrayList<>();
			if (rs.next()) {
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
		boolean rtn = false;
		try {
			con = DriverManager.getConnection(this.getConnection());
			con.setAutoCommit(false);
			// ユーザーテーブル更新
			String sql = "UPDATE	User "
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
			rtn = pstmt.executeUpdate() > 0;
			
			if (rtn) {
				rtn= delThread(pstmt, con, user, account);
			}
						
			if (rtn) {
				con.commit();
			} else {
				con.rollback();
			}
			pstmt.close();
		} catch (SQLException e) {
			try {
				con.rollback();
				rtn = false;
			} catch (SQLException e2) {
				e2.printStackTrace();
				rtn = false;
			} 
		} finally {
			try {
				if (con != null) {
					con.close();
				}	
			} catch (SQLException e3) {
				e3.printStackTrace();
				rtn = false;
			}
		}
		return rtn;
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
		boolean rtn = true;
		if (threadIdList.size() > 0) {
			sql = threadDao.deleteThreadSql();		 
			sql += threadDao.ByMakeUserIdSql();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, account.getUserId());
			rtn = pstmt.executeUpdate() > 0;
		}
		if (rtn) {
			rtn = delCommentByThreadId(pstmt, con, user, account, threadIdList);
		}
		rs.close();
		return rtn;
	}
	
	public boolean delCommentByThreadId(PreparedStatement pstmt, Connection con, UserInfo user, UserInfo account, ArrayList<Integer> threadIdList) throws SQLException {
		// コメントテーブル(スレッドID)更新
		boolean rtn = true; 
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
					rtn = pstmt.executeUpdate() > 0;
					if (!rtn) {
						break;
					}
				}
			}
		}
		if (rtn) {
			rtn = delCommentByPostUserId(pstmt, con, user, account, commentDao);
		}
		rs.close();
		return rtn;
	}
	
	public boolean delCommentByPostUserId(PreparedStatement pstmt, Connection con, UserInfo user, UserInfo account, CommentDAO commentDao) throws SQLException {
		// コメントテーブル(投稿ユーザーID)更新
		boolean rtn = true; 
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
			rtn = pstmt.executeUpdate() > 0;
		}
		rs.close();
		return rtn;
	}
	
	public boolean modifyAccount(UserInfo user, UserInfo account, String lift) {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try(Connection con = DriverManager.getConnection(this.getConnection())) {
			// ユーザーテーブル更新
			String sql = "UPDATE User "
					   + "SET userName = ?      ,pass = ?                ,email = ?   ,birth = ?     ,genderId = ? "
					   + "	, dispUpdUserId = ? ,dispUpdDate = GETDATE() ,manager = ? ,updUserId = ? ,updDate = GETDATE() ";
			if (account.getErrorCount() >= 3 && Integer.parseInt(lift) == 1) {
				sql += " , errorCount = 0 ";
			}
			sql += "WHERE userId = ? ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getUserName());
			pstmt.setString(2, account.getUserId());
			pstmt.setString(3, account.getUserId());
			boolean rtn = pstmt.executeUpdate() > 0;
			pstmt.close();
			return rtn;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 		
	}
	/* 追加 2022/02/10(木)　大野賢一朗 end */
}
