/*
	処理内容:	性別DAO
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenderDAO extends ProcDAO {
	
	public ArrayList<GenderInfo> findGenderList() {
		try (Connection con = connectDb()) {
			String sql =	"SELECT	genderId		AS id " +
							"	  , genderName		AS name " +
							"FROM	Gender ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<GenderInfo> resultGenderList = new ArrayList<>();
			while (rs.next()) {
				resultGenderList.add(new GenderInfo(rs.getInt("id"), rs.getString("name")));
			}
			rs.close();
			pstmt.close();
			return resultGenderList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PreparedStatement setPstmt (PreparedStatement pstmt) throws SQLException {
		return pstmt;
	}

}
