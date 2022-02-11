package bulletinBoard;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				passHash =  rs.getString(pass);
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
//		
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
	
}
