<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.ThreadDispInfo" %>
<%@ page import="bulletinBoard.CommentInfo" %>
<%@ page import="java.util.ArrayList" %>

<%
String deleteMessage = (String)request.getAttribute("sendDeleteMessage");
	String commentMessage = (String)request.getAttribute("sendCommentMessage");
	ThreadDispInfo threadInfo = null;
	if (request.getAttribute("sendThreadInfo") instanceof ThreadDispInfo) {
		threadInfo = (ThreadDispInfo)request.getAttribute("sendThreadInfo");
	}
	ArrayList<CommentInfo> commentList = null;
	if (request.getAttribute("sendCommentList") instanceof ArrayList) {
		commentList = (ArrayList<CommentInfo>)request.getAttribute("sendCommentList");
	}
%>    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド詳細</title>
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="ServCategorySearchList">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		<%
			if (deleteMessage != null && deleteMessage.equals("コメントが削除されました")) {
		%>
				<div>
					<%= deleteMessage %>
				</div>
		<%	
			}
		%>
		
		
	
		<%
			if (commentList != null) {
				for (int i = 0; i < commentList.size(); i++) {
		%>
					<p><%= i + 2 %>. <%= commentList.get(i).getPostUserName() %> <%= commentList.get(i).getPostDate() %></p>
					<p><%= commentList.get(i).getComment() %></p>
					<form action="ServletCommentDelConfirm" method="post">
						<input type="submit" value="削除">
						<input type="hidden" name="commentId" value="commentId">
					</form>
					<br>
		<%	
				}
			}
		%>
		
		<%
			if (commentList == null || commentList.size() < 50) {
		%>
				<form action="ServletCommentPost" method="post">
					<input type="submit" value="コメントする">
				</form>
		<%
			}
		%>		
		
		<%
			if (commentMessage != null && commentMessage.equals("コメント数が50件以上のため、コメントすることができません")) {
		%>
				<div>
					<%= commentMessage %>
				</div>
		<%	
			}
		%>
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
	</body>
</html>