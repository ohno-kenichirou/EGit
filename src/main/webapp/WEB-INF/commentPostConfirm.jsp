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
		<link rel="shortcut icon" href="img/bulletin_board.ico">
		<link rel="stylesheet" type="text/css" href="css/design.css">
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />
		
		<div class="text-center">
			<br>
			<p>
				入力内容を確認してください
			</p>
			<br>
			
			<div class="inline-block">
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
			</div>
			
			<br>
			<br>
			<form action="ServletCommentPostConfirm" method="post">
				<input class="add width5" type="submit" value="投稿">
				<input type="hidden" name="threadId" value="<%= newComment.getThreadId() %>">
				<input type="hidden" name="comment" value="<%= newComment.getComment() %>">
				<input type="hidden" name="newComment" value="yes">
			</form>
			<br>
			<br>
			<br>			
			<a href="ServletCommentPost?threadId=<%= newComment.getThreadId() %>">コメント作成画面へ戻る</a>
			<br>
			<br>
		</div>
	</body>
</html>