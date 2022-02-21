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
		<link rel="stylesheet" type="text/css" href="css/design.css">
		<link rel="shortcut icon" href="img/bulletin_board.ico">
	</head>
	<body>
	 	<jsp:include page="header.jsp" flush="true" />
		
		<form action="ServletCategorySearchList" method="post">
			<p class="text-center">
				<span class="search-margin">
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
				</span>	
				<span class="search-margin">														
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
				</span>			
				<input type="hidden" name="update" value="search">
				<span class="search-margin">
					<input class="default width5" type="submit" value="検索">
				</span>
			</p>
		</form>
		<hr>
		
		<%
			if (message != null && message != "") {
		%>		
				<br>
				<div class="caution-text">
					<%= message %>
				</div>
		<%	
			}
		%>
		<br>
		<div class="text-center">
			<form action="ServletCategoryAdd" method="get">
				<input class="add width12" type="submit" value="カテゴリー追加">
			</form>
		</div>
		<br>
		<div class="thread text-center">
			<%
				for (CategoryNameDisp category : categoryList) {
			%>
					<div class="thread-info  backcolor-gray">
						<div class="inline-block category-name">
							<%= category.getCategoryName() %>
						</div>
						
						<div class="inline-block category-update-button">
							<form action="ServletCategorySearchList" method="post">							
								<input class="modify width5" type="submit" value="修正">
								<input type="hidden" name="update" value="modify">
								<input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
								<input type="hidden" name="categoryName" value="<%= category.getCategoryName() %>">
								<input type="hidden" name="categoryKana" value="<%= category.getCategoryKana() %>">							
							</form>
						</div>
						
						<div class="inline-block category-update-button" >
							<form action="ServletCategorySearchList" method="post">								
								<input class="delete width5" type="submit" value="削除">
								<input type="hidden" name="update" value="delete">
								<input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
								<input type="hidden" name="categoryName" value="<%= category.getCategoryName() %>">
								<input type="hidden" name="categoryKana" value="<%= category.getCategoryKana() %>">								
							</form>
						</div>
								
					</div>
					<br>					
			<%
				}
			%>
		</div>
		
		<br>
		<div class="text-center">
			<%
				for (int i = 1; i <= pagination; i++) {
			%>
					<span class="pagination-margin"><a href="ServletCategorySearchList?page=<%= i  %>"><%= i %></a></span>
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