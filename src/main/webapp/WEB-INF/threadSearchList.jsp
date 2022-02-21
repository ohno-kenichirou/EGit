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
		<link rel="stylesheet" type="text/css" href="css/design.css">
		<link rel="shortcut icon" href="img/bulletin_board.ico">
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />
		
		<form action="ServletThreadSearchList" method="post">
			<p class="text-center">
				
				<span class="search-margin">
					<label for="searchWord">タイトル:</label>
					<input id="searchWord" type="text" name="searchWord" placeholder="タイトル入力" <%
																					if (searchWord != null) {
																				%>
																						value="<%= searchWord %>"
																				<%
																					}
																				%>>
				</span>
				<span class="search-margin">
					<input type="radio" name="match" value="part" id="part" <%
																				if (threadSearch == null || (threadSearch != null && threadSearch.getMatch().equals("part"))) {	
																			%>
																					checked="checked"
																			<%
																				}
																			%>		
																			><label for="part">部分一致</label>
					<input type="radio" name="match" value="all" id="all" <%
																				if (threadSearch != null && threadSearch.getMatch().equals("all")) {	
																		   %>
																		   			checked="checked"
																		  <%
																				}
																		  %>
																		  ><label for="all">完全一致</label>
				</span>
			</p>
			<p class="text-center">
				<span class="search-margin">
					<label for="category">カテゴリー:</label>
					<select class="input-width" id="category" name="categoryId">
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
				</span>
				<input class="search-margin default width5" type="submit" value="検索">
			</p>
		</form>
		<hr>
		<%
			if (message != null && !message.equals("")) {
		%>
				<br>
				<div class="caution-text">
					<%= message %>
				</div>
		<%	
			}
			if (user != null) {
		%>
		<br>
		<div class="text-center">
			<form action="ServletThreadCreate" method="post">
				<input class="add width12" type="submit" value="スレッドを立てる">
			</form>
		</div>
		<br>
		<%
			}
			
			if (threadList != null) {
				for (ThreadDispInfo thread : threadList) {
					
		%>				
					<div class="thread">
						<form action="ServletThreadDelConfirm" method="post">
							<p class="thread-info backcolor-gray">
								<a class="thread-linkFont" href="ServletThreadDetail?threadId=<%= thread.getThreadId() %>"><%= thread.getTitle() %></a>
								<%
									if (user != null && user.getManager() == 1) {
								%>
										<span>								
											<input class="thread-infoMargin delete width5" type="submit" value="削除">
											<input type="hidden" name="threadId" value="<%= thread.getThreadId() %>">								
										</span>
								<%
									}
								%>						
								<br>
								<span class="thread-infoMargin">カテゴリー:<%= thread.getCategory() %></span>
								<span class="thread-infoMargin">作成者:<%= thread.getCreateUserName() %></span>
								<span class="thread-infoMargin">作成日:<%= thread.getCreateDate().substring(0, 11) %></span>								
							</p>
						</form>
					</div>
		<%
				}
			}
		%>
		
		<br>
		<br>
		<div class="text-center">
			<%
				for (int i = 1; i <= pagination; i++) {
			%>		
					<span class="pagination-margin"><a href="ServletThreadSearchList?page=<%= i  %>"><%= i %></a></span>
			<%
					if (i == 10) {
						break;
					}
				}
			%>
		</div>
		<br>
		<br>
	</body>
</html>