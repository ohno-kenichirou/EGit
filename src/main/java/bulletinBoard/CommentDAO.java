package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class CommentDAO {

	private String sqlserver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String connection = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
	
	public String getSqlserver() {
		return sqlserver;
	}
	
	public String getConnection() {
		return connection;
	}
	
	public ArrayList<CommentInfo> searchAndSetList(int threadId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "SELECT commentId, [User].userName AS postUserName, postDate AS postDate, content AS comment "
					   + "FROM Comment INNER JOIN [User] ON Comment.postUserId = [User].userId "
					   + "WHERE threadId = ? AND Comment.delFlg = 0";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<CommentInfo> commentList = new ArrayList<>();
//			int i = 0;
			while (rs.next()) {
//				if (i <= 50) {
					commentList.add(new CommentInfo(rs.getInt("commentId"), rs.getString("postUserName"), rs.getString("postDate").substring(0, 19), rs.getString("comment")));
//				} else {
//					break;
//				}
//				i++;
			}
			Collections.sort(commentList);
			
			rs.close();
			pstmt.close();
			
			return commentList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public DeleteCommentInfo deleteCommentDisp(int threadId, int commentId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "SELECT threadId, commentId, [User].userName AS postUserName, postDate AS postDate, content AS comment "
					   + "FROM Comment INNER JOIN [User] ON Comment.postUserId = [User].userId "
					   + "WHERE threadId = ? AND commentId = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			pstmt.setInt(2, commentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			DeleteCommentInfo delComment;
			if (rs.next()) {
				delComment = new DeleteCommentInfo(rs.getInt("threadId"), rs.getInt("commentId"), rs.getString("postUserName"), rs.getString("postDate").substring(0, 19), rs.getString("comment"));
			} else {
				delComment = null;
			}
			
			rs.close();
			pstmt.close();
			
			return delComment;
				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String deleteComment(int threadId, int commentId) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "コメントの削除に失敗しました";
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "UPDATE Comment "
					   + "SET delFlg = 1 "
					   + "WHERE threadId = ? AND commentId = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, threadId);
			pstmt.setInt(2, commentId);
			
			int result = pstmt.executeUpdate();
			pstmt.close();
			
			switch (result) {
				case 1:
					return "コメントが削除されました";
				default:
					return "コメントの削除に失敗しました";
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			return "コメントの削除に失敗しました";
		}
	}
	
	public boolean postComment(UserInfo user, NewCommentInfo newComment) {
		
		try {
			Class.forName(this.getSqlserver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try (Connection con = DriverManager.getConnection(this.getConnection());) {
			
			String sql = "INSERT INTO Comment(threadId, postUserId, postDate, content, delFlg, insUserId, insDate) "
					   + "VALUES(?, ?, (SELECT CURRENT_TIMESTAMP), ?, 0, ?, (SELECT CURRENT_TIMESTAMP))";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newComment.getThreadId());
			pstmt.setString(2, user.getUserId());
			pstmt.setString(3, newComment.getComment());
			pstmt.setString(4, user.getUserId());
			
			int result = pstmt.executeUpdate();
			pstmt.close();
			
			switch (result) {
				case 1:
					return true;
				default:
					return false;
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/* 追加 2022/02/10(木)　大野賢一朗 start */
	public String findCommentIdListSql() {
		return "SELECT	commentId "
			 + "FROM	Comment "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String deleteCommentSql() {
		return "UPDATE	Comment "
			 + "SET		delFlg = 1 "
			 + "WHERE	delFlg = 0 AND ";
	}
	
	public String ByPostUserIdSql() {
		return "     	postUserId = ? ";
	}
	
	public String ByThreadIdSql() {
		return "     	threadId = ? ";
	}
	
	/* 追加 2022/02/10(木)　大野賢一朗 end */
	
}
