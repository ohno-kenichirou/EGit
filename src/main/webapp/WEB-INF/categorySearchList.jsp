<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.CategoryNameDisp" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.CategorySearchInfo" %>
<%
	CategorySearchInfo categorySearch = (CategorySearchInfo)session.getAttribute("CategorySearchInfo");
	String searchWord = null;
	if (categorySearch != null) {
		searchWord = categorySearch.getSearchWord();
	}
	
	String message = (String)request.getAttribute("message");
	
	ArrayList<CategoryNameDisp> categoryList = null;
	if (session.getAttribute("CategoryList") instanceof ArrayList) {
		categoryList = (ArrayList<CategoryNameDisp>)session.getAttribute("CategoryList");
	}	
	
	Integer pagination = 1;
	if (session.getAttribute("CategoryPagination") instanceof Integer) {
		pagination = (Integer)session.getAttribute("CategoryPagination");
	}
	
	UserInfo user = (UserInfo)session.getAttribute("User");
	int threadId;
	
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>カテゴリー一覧・検索</title>
		<link rel="stylesheet" type="text.css" href="../css.css">
	</head>
	<body>
	 	<jsp:include page="header.jsp" flush="true" />
		
		<form action="ServletCategorySearchList" method="post">
			<label>
				<span>カテゴリー名:</span>
			</label>			
			<input type="text" name="searchWord" placeholder="カテゴリー名入力" <%
			  																	if (searchWord != null) {
			  																%>
			  																		value="<%= searchWord %>"
			  																<%		
			  																	}
																			%>>
				<input type="radio" name="match" value="part" id="part" <%
																			if (categorySearch == null || (categorySearch != null && categorySearch.getMatch().equals("part"))) {	
																		%>
																				checked="checked"
																		<%
																			}
																		%>		
																		><label for="part">部分一致</label>
				<input type="radio" name="match" value="all" id="all" <%
																			if (categorySearch != null && categorySearch.getMatch().equals("all")) {	
																	   %>
																	   			checked="checked"
																	  <%
																			}
																	  %>
																	  ><label for="all">完全一致</label>
			<input type="submit" value="検索">
		</form>
		<hr>
		
		<%
			if (message != null && message != "") {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		<form action="ServletCategoryAdd" method="get">
			<input type="submit" value="カテゴリー追加">
		</form>
		
		<%
			for (CategoryNameDisp category : categoryList) {
		%>
				
				<p>
					<%= category.getCategoryName() %>
					<form action="ServletCategorySearchList" method="post">
						<input type="submit" value="修正">
						<input type="hidden" name="update" value="modify">
						<input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
						<input type="hidden" name="categoryName" value="<%= category.getCategoryName() %>">
						<input type="hidden" name="categoryKana" value="<%= category.getCategoryKana() %>">
					</form>
					<form action="ServletCategorySearchList" method="post">
						<input type="submit" value="削除">
						<input type="hidden" name="update" value="delete">
						<input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
						<input type="hidden" name="categoryName" value="<%= category.getCategoryName() %>">
						<input type="hidden" name="categoryKana" value="<%= category.getCategoryKana() %>">
					</form>
				</p>
				
		<%
			}
		%>
		
		<br>
		
		<%
			for (int i = 1; i <= pagination; i++) {
		%>
				<a href="ServletCategorySearchList?page=<%= i  %>"><%= i %></a>	
		<%
				if (i == 10) {
					break;
				}
			}
		%>
	
	</body>
</html>