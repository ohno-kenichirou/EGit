<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.ThreadDispInfo" %>
<%@ page import="bulletinBoard.CommentInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>

<%
	String deleteMessage = (String)request.getAttribute("sendDeleteMessage");
	String commentMessage = (String)request.getAttribute("sendCommentMessage");
	ThreadDispInfo threadInfo = null;
	if (session.getAttribute("ThreadInfo") instanceof ThreadDispInfo) {
		threadInfo = (ThreadDispInfo)session.getAttribute("ThreadInfo");
	}
	ArrayList<CommentInfo> commentList = null;
	if (session.getAttribute("CommentList") instanceof ArrayList) {
		commentList = (ArrayList<CommentInfo>)session.getAttribute("CommentList");
	}
	UserInfo user = (UserInfo)session.getAttribute("User");
	int threadId = threadInfo.getThreadId();
	int commentId;
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

		<%
			if (deleteMessage != null && !deleteMessage.equals("")) {
		%>
				<div>
					<%= deleteMessage %>
				</div>
		<%	
			}
		%>
		
		<%
			if (threadInfo != null) {
				
		%>
				<h1><%= threadInfo.getTitle() %></h1>
				<span>カテゴリー:<%= threadInfo.getCategory() %></span>
				<p>
					<span>1. </span><%= threadInfo.getCreateUserName() %><span> <%= threadInfo.getCreateDate() %></span>
					<br>
					<span><%= threadInfo.getComment() %></span>
				</p>
				
				<br>
		<%	
				
			}
		%>
		
		
		
	
		<%
			if (commentList != null) {
				for (int i = 0; i < commentList.size(); i++) {
					
					commentId = commentList.get(i).getCommentId();
		%>
					<p><%= i + 2 %>. <%= commentList.get(i).getPostUserName() %> <%= commentList.get(i).getPostDate() %></p>
					<p><%= commentList.get(i).getComment() %></p>
					<%
						if (user != null && user.getManager() == 1) {
					%>
					
							<form action="ServletCommentDelConfirm" method="post">
								<input type="submit" value="削除">
								<input type="hidden" name="commentId" value="<%= commentId %>">
								<input type="hidden" name="threadId" value="<%= threadId %>">
							</form>
					<%
						}
					%>
					<br>
		<%	
				}
			}
		%>
		
		<%
			if ((commentList == null || commentList.size() < 50) && user != null) {
		%>
				<form action="ServletCommentPost" method="post">
					<input type="submit" value="コメントする">
					<input type="hidden" name="threadId" value="<%= threadId %>">
				</form>
		<%
			}
		%>		
		
		<%
			if ((commentMessage != null && !commentMessage.equals("")) && user != null) {
		%>
				<div>
					<%= commentMessage %>
				</div>
		<%	
			}
		%>
		
		<br>
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
	</body>
</html>