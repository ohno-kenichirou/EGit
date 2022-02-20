/**
	処理内容:	ユーザーID作成DAO
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class MakeUserIdDAO {
	private String sqlserver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String connection = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
	
	public String getSqlserver() {
		return sqlserver;
	}

	public String getConnection() {
		return connection;
	}

	/**
	 * 	処理内容：アカウント登録のユーザーIDを取得
	 * 
	 * 	作成者：大野賢一朗　作成日：2022/02/14(月)
	 */
	public String getUserId() {
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Connection con = null;
		boolean isRes = false;
		String ret = null;
		try {
			con = DriverManager.getConnection(this.getConnection());
			con.setAutoCommit(false);
			MakeUserIdInfo info = null;
			// 現在年月日のIDアルファベットとIDナンバーを取得
			info = getMakeUserIdData(con);
			// 現在年月日のデータが無ければ作成
			if (info == null) {
				isRes = insMakeUserId(con);
				if (isRes) {
					info = getMakeUserIdData(con);
				}
			}
			if (info != null) {
				isRes = true;
			}
			Date thisDate = null;
			String idAlphabet = null;
			int idNo = 0;
			if (isRes) {
				thisDate = info.getThisDate();
				idAlphabet = info.getIdAlphabet();
				idNo = info.getIdNo();
				// ユーザーID作成テーブルの更新
				isRes = updMakeUserId(con, idAlphabet, idNo);
			}
			if (isRes) {
				// 返すユーザーIDを作成
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				ret = idAlphabet + sdf.format(thisDate) + String.format("%02d", idNo);
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			try {
				e.printStackTrace();
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
		return ret;
	}
		
	/**
	 * 	処理内容：ユーザーID作成テーブルから現在年月日のデータを取得
	 * 
	 * 	作成者：大野賢一朗　作成日：2022/02/14(月)
	 */
	public MakeUserIdInfo getMakeUserIdData(Connection con) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT thisDate, idAlphabet, idNo FROM MakeUserId WHERE thisDate = CONVERT(DATE,GETDATE())";
		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		MakeUserIdInfo info = null;
		if (rs.next()) {
			info = new MakeUserIdInfo(rs.getDate("thisDate"), rs.getString("idAlphabet"), rs.getInt("idNo"));
		}
		pstmt.close();
		rs.close();
		return info;
	}
	
	/**
	 * 	処理内容：ユーザーID作成テーブルに現在年月日のデータを作成
	 * 
	 * 	作成者：大野賢一朗　作成日：2022/02/14(月)
	 */
	public boolean insMakeUserId(Connection con) throws SQLException {
		String sql = "INSERT INTO MakeUserId (thisDate)	VALUES (CONVERT(DATE,GETDATE())) ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		boolean ret = pstmt.executeUpdate() > 0;
		pstmt.close();
		return ret;
	}
	
	/**
	 * 	処理内容：ユーザーID作成テーブル更新
	 * 
	 * 	作成者：大野賢一朗　作成日：2022/02/14(月)
	 */
	public boolean updMakeUserId(Connection con, String idAlphabet, int idNo) throws SQLException {
		String sql = "UPDATE MakeUserId SET idAlphabet = ?, idNo = ?　WHERE　thisDate = CONVERT(DATE,GETDATE()) ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		String updAlphabet = "";
		int updNo = 0;
		if (idNo >= 99) {
			updAlphabet = getIncAlphabet(idAlphabet);
			updNo = 1;
		} else {
			updAlphabet = idAlphabet;
			updNo = ++idNo;
		}
		pstmt.setString(1, updAlphabet);
		pstmt.setInt(2, updNo);
		boolean ret = pstmt.executeUpdate() > 0;
		pstmt.close();
		return ret;
	}
		
	/**
	 * 	処理内容：アルファベットインクリメント
	 * 
	 * 	作成者：大野賢一朗　作成日：2022/02/14(月)
	 */
	public String getIncAlphabet(String idAlphabet) {
		char beforeChar = idAlphabet.charAt(0);
		char newChar = ++beforeChar;
		String ret =  String.valueOf(newChar);;
		return ret;
	}
}
