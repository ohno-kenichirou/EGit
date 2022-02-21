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
		<link rel="shortcut icon" href="img/bulletin_board.ico">
		<link rel="stylesheet" type="text/css" href="css/design.css">	
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />
		
		<div class="text-center">
			<br>
			<p>
				削除内容を確認してください
			</p>
			<br>
			
			<%
				if (delComment != null) {
			%>
					<div class="inline-block">
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
					</div>
					<br>
					<br>
					
					<form action="ServletCommentDelConfirm" method="post">
						<input class="delete width5" type="submit" value="削除">
						<input type="hidden" name="threadId" value="<%= threadId %>">
						<input type="hidden" name="commentId" value="<%= commentId %>">
						<input type="hidden" name="delete" value="yes">
					</form>
			
			<%
				}
			%>
			
			<br>
			<br>
			<br>			
			<a href="ServletThreadDetail?threadId=<%= threadId %>">スレッド詳細画面へ戻る</a>
			<br>
			<br>
		</div>
	</body>
</html>