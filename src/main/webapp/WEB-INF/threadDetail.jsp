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
		<link rel="stylesheet" type="text/css" href="css/design.css">
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />

		<%
			if (deleteMessage != null && !deleteMessage.equals("")) {
		%>
				<br>
				<div class="caution-text">
					<%= deleteMessage %>
				</div>
		<%	
			}
		%>
		
		<div class="thread">
		
		<%
			if (threadInfo != null) {	
		%>
				<div class="thread-info">
					<h1><%= threadInfo.getTitle() %></h1>
					<span>カテゴリー:<%= threadInfo.getCategory() %></span>
					<br>
					<br>
					<div class="backcolor-gray">
						<span>1.</span><span class="thread-infoMargin"><%= threadInfo.getCreateUserName() %></span><span class="thread-infoMargin"><%= threadInfo.getCreateDate() %></span>
						<br>
						<span><%= threadInfo.getComment() %></span>
					</div>
				</div>
		<%		
			}
		%>
		
		<%
			if (commentList != null) {
				for (int i = 0; i < commentList.size(); i++) {
					commentId = commentList.get(i).getCommentId();
		%>
					<div class="thread-info">
						<div class="backcolor-gray">
						<span><%= i + 2 %>.</span><span class="thread-infoMargin"><%= commentList.get(i).getPostUserName() %></span><span class="thread-infoMargin"><%= commentList.get(i).getPostDate() %></span>
						<br>
						<span><%= commentList.get(i).getComment() %></span>
						</div>
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
					</div>
		<%	
				}
			}
		%>
		<br>
		<%
			if ((commentList == null || commentList.size() < 50) && user != null) {
		%>
				<div class="text-center">
					<form action="ServletCommentPost" method="post">
						<input type="submit" value="コメントする">
						<input type="hidden" name="threadId" value="<%= threadId %>">
					</form>
				</div>
		<%
			}
		%>		
		
		<%
			if ((commentMessage != null && !commentMessage.equals("")) && user != null) {
		%>
				<br>
				<div class="text-center">
					<%= commentMessage %>
				</div>
		<%	
			}
		%>
		
		</div>
		<br>
		<br>
		<div class="text-center">
			<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
		</div>
		<br>
		<br>
	</body>
</html>