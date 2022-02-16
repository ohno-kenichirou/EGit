<!-- 
	作成者:高橋 作成日:2022/02/04
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.ThreadDispInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.CategoryNameDisp" %>
<%@ page import="bulletinBoard.ThreadSearchInfo" %>
<%
	ThreadSearchInfo threadSearch = (ThreadSearchInfo)session.getAttribute("ThreadSearchInfo");
	
	String searchWord = null;
	if (threadSearch != null) {
		searchWord = threadSearch.getSearchWord();
	}
	
	String message = (String)request.getAttribute("sendMessage");
	
	ArrayList<ThreadDispInfo> threadList = null;
	if (session.getAttribute("ThreadList") instanceof ArrayList) {
		threadList = (ArrayList<ThreadDispInfo>)session.getAttribute("ThreadList");
	}
	
	ArrayList<CategoryNameDisp> categoryList = null;
	if (session.getAttribute("CategoryList") instanceof ArrayList) {
		categoryList = (ArrayList<CategoryNameDisp>)session.getAttribute("CategoryList");
	}
	
	UserInfo user = (UserInfo)session.getAttribute("User");
	
	Integer pagination = 1;
	if (session.getAttribute("ThreadPagination") instanceof Integer) {
		pagination = (Integer)session.getAttribute("ThreadPagination");
	}
	
	int threadId;
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
		
		<form action="ServletThreadSearchList" method="post">
			<p>
				<span>タイトル:</span>
				<input type="text" name="searchWord" placeholder="タイトル入力" <%
																				if (searchWord != null) {
																			%>
																					value="<%= searchWord %>"
																			<%
																				}
																			%>>
				<input type="radio" name="match" value="part" id="part" checked="checked"><label for="part">部分一致</label>
				<input type="radio" name="match" value="all" id="all"><label for="all">完全一致</label>
			</p>
			<p>
				<span>カテゴリー:</span>
				<select name="categoryId">
					<option value="0">検索条件に含まない</option>
					<%
						if (categoryList != null && categoryList.size() != 0) {
							for (CategoryNameDisp category : categoryList) {
					%>
								<option value="<%= category.getCategoryId() %>" <%
																					if (threadSearch != null && threadSearch.getCategoryId() == category.getCategoryId()) {
																				%>
																						selected
																				<%
																					}
																				%>>
									<%= category.getCategoryName() %>
								</option>
					<%			
							}
						}
					%>
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
			if (user != null) {
		%>
		
				<form action="ServletThreadCreate" method="post">
					<input type="submit" value="スレッドを立てる">
				</form>
		
		<%
			}
			
			if (threadList != null) {
				for (ThreadDispInfo thread : threadList) {
					
		%>
				
					<p>
						<a href="ServletThreadDetail?threadId=<%= thread.getThreadId() %>"><%= thread.getTitle() %></a>
						<br>
						<span>カテゴリー:<%= thread.getCategory() %></span>
						<span>作成者:<%= thread.getCreateUserName() %></span>
						<span>作成日:<%= thread.getCreateDate().substring(0, 11) %></span>
						
						<%
							if (user != null && user.getManager() == 1) {
						%>
								<form action="ServletThreadDelConfirm" method="post">
									<input type="submit" value="削除">
									<input type="hidden" name="threadId" value="<%= thread.getThreadId() %>">
								</form>
						<%
							}
						%>
						
					</p>
					
		<%
				}
			}
		%>
		
		<br>
		
		<%
			for (int i = 1; i <= pagination; i++) {
		%>
				<a href="ServletThreadSearchList?page=<%= i  %>"><%= i %></a>	
		<%
				if (i == 10) {
					break;
				}
			}
		%>
	</body>
</html>