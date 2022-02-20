<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.DeleteCommentInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	DeleteCommentInfo delComment = null;
	if (request.getAttribute("sendDeleteCommentInfo") instanceof DeleteCommentInfo) {
		delComment = (DeleteCommentInfo)request.getAttribute("sendDeleteCommentInfo");
	}
	UserInfo user = (UserInfo)session.getAttribute("User");
	int threadId = delComment.getThreadId();
	int commentId = delComment.getCommentId();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント削除内容確認</title>
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			
			<%
				if (user != null && user.getManager() == 1) {
			%>
					<a href="ServletCategorySearchList">カテゴリー一覧</a>
					<a href="ServletAccountSearchList">アカウント一覧</a>
			<%		
				}
				if (user != null) {
			%>
					<a href="ServletLogout">ログアウト</a>	
			<%
				} else {
			%>
					<a href="ServletLogin">ログイン</a>	
			<%
				}
			%>		
				
		</header>
		<hr>
		
		<p>
			削除内容を確認してください
		</p>
		
		<%
			if (delComment != null) {
		%>
		
				<table>
					<tr>
						<th colspan="2">
							コメント情報
						</th>
					</tr>
					<tr>
						<th>ユーザー名</th>
						<td><%= delComment.getPostUserName() %></td>
					</tr>
					<tr>
						<th>コメント投稿日</th>
						<td><%= delComment.getPostDate().substring(0, 19) %></td>
					</tr>
					<tr>
						<th>コメント内容</th>
						<td><%= delComment.getComment() %></td>
					</tr>
					
				</table>
				
				<br>
				
				<form action="ServletCommentDelConfirm" method="post">
					<input type="submit" value="削除">
					<input type="hidden" name="threadId" value="<%= threadId %>">
					<input type="hidden" name="commentId" value="<%= commentId %>">
					<input type="hidden" name="delete" value="yes">
				</form>
		
		<%
			}
		%>
		
		<br>
		
		<a href="ServletThreadDetail?threadId=<%= threadId %>">スレッド詳細画面へ戻る</a>
		
	</body>
</html>