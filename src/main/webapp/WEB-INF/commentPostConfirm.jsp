<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewCommentInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	NewCommentInfo newComment = null;
	if (session.getAttribute("NewCommentInfo") instanceof NewCommentInfo) {
		newComment = (NewCommentInfo)session.getAttribute("NewCommentInfo");
	}
	UserInfo user = (UserInfo)session.getAttribute("User");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント投稿内容確認</title>
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
			入力内容を確認してください
		</p>
		
		<table>
			<tr>
				<th>ユーザー名</th>
				<td><%= user.getUserName() %></td>
			</tr>
			<tr>
				<th>コメント内容</th>
				<td><%= newComment.getComment() %></td>
			</tr>
			
		</table>
		
		<br>
		
		<form action="ServletCommentPostConfirm" method="post">
			<input type="submit" value="投稿">
			<input type="hidden" name="threadId" value="<%= newComment.getThreadId() %>">
			<input type="hidden" name="comment" value="<%= newComment.getComment() %>">
			<input type="hidden" name="newComment" value="yes">
		</form>
		
		<br>
		
		<a href="ServletCommentPost?threadId=<%= newComment.getThreadId() %>">コメント作成画面へ戻る</a>
		
	</body>
</html>