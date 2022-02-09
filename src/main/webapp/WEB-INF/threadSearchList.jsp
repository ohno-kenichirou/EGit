<!-- 
	作成者:高橋 作成日:2022/02/04
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.ThreadDispInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	String searchWord = (String)request.getAttribute("searchWord");
	if (searchWord == null) {
		searchWord = "タイトル入力";
	}
	String message = (String)request.getAttribute("message");
	ArrayList<ThreadDispInfo> threadList = null;
	if (request.getAttribute("sendThreadList") instanceof ArrayList) {
		threadList = (ArrayList<ThreadDispInfo>)request.getAttribute("sendThreadList");
	}			
	UserInfo user = (UserInfo)session.getAttribute("User");
	int i;
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド一覧・検索</title>
		<link rel="stylesheet" type="text.css" href="../cssLogout.css">
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			
			<%
				if (user != null && user.getManager() == 1) {
			%>
					<a href="ServletCategorySearchList">カテゴリー一覧</a>
					<a href="#">アカウント一覧</a>
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
		
		<form action="#">
			<p>
				<span>タイトル:</span>
				<input type="text" name="searchWord	" placeholder="<%= searchWord %>">
				<input type="radio" neme="match" value="部分一致" checked="checked">部分一致
				<input type="radio" neme="match" value="完全一致">完全一致
			</p>
			<p>
				<span>カテゴリー:</span>
				<select name="searchCategory">
					<option value="no">検索条件に含まない</option>
				</select>
				<input type="submit" value="検索">
			</p>
		</form>
		<hr>
		
		<%
			if (message != null && !message.equals("")) {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		<form action="ServletThreadCreate" method="post">
			<input type="submit" value="スレッドを立てる">
		</form>
		
		
		
		<%
			for (ThreadDispInfo thread : threadList) {
				i = thread.getThreadId();
		%>
				
				<p>
					<a href="ServletThreadDetail?threadInfo=<%= i %>"><%= thread.getTitle() %></a>
					<br>
					<span>カテゴリー:<%= thread.getCategory() %></span>
					<span>作成者:<%= thread.getCreateUserName() %></span>
					<span>作成日:<%= thread.getCreateDate().substring(0, 11) %></span>
					<form action="ServletThreadDelConfirm" method="post">
						<input type="submit" value="削除">
						<input type="hidden" name="update" value="delete">
						<input type="hidden" name="threadId" value="<%= thread.getThreadId() %>">
					</form>
				</p>
				
		<%
			}
		%>
		
		<br>
		
		<a href="ServletThreadSearchList?page=1">1</a>
		<a href="ServletThreadSearchList?page=2">2</a>
		<a href="ServletThreadSearchList?page=3">3</a>
		<a href="ServletThreadSearchList?page=4">4</a>
		<a href="ServletThreadSearchList?page=5">5</a>
		<a href="ServletThreadSearchList?page=6">6</a>
		<a href="ServletThreadSearchList?page=7">7</a>
		<a href="ServletThreadSearchList?page=8">8</a>
		<a href="ServletThreadSearchList?page=9">9</a>
		<a href="ServletThreadSearchList?page=10">10</a>
	</body>
</html>