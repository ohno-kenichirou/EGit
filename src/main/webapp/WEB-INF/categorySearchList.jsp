<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.CategoryListInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	String searchCategoryWord = (String)request.getAttribute("searchCategoryWord");
	if (searchCategoryWord == null) {
		searchCategoryWord = "カテゴリー名入力";
	}
	
	String message = (String)request.getAttribute("message");
	
	String searchWord = (String)request.getAttribute("searchWord");
	if (searchWord == null) {
		searchWord = "タイトル入力";
	}
	ArrayList<CategoryListInfo> categoryList = null;
	if (request.getAttribute("sendCategoryList") instanceof ArrayList) {
		categoryList = (ArrayList<CategoryListInfo>)request.getAttribute("sendCategoryList");
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
		
		<form action="ServletCategorySearchList" method="post">
			<label>
				<span>カテゴリー名:</span>
			</label>			
			<input type="text" id="searchCategoryWord" name="searchCategoryWord" placeholder="<%= searchCategoryWord %>">
			<input type="radio" name="selectMatch" value="partial" checked="checked">部分一致
			<input type="radio" name="selectMatch" value="perfect">完全一致
			<input type="hidden" name="btn" value="search">
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
		
		<form action="ServletCategorySearchList" method="post">
			<input type="hidden" name="btn" value="add">
			<input type="submit" value="カテゴリー追加">
		</form>
		
		<%
			for (CategoryListInfo category : categoryList) {
		%>
			
			<p>
				<div style="display:inline-flex;">
					<%= category.getCategoryName() %>
				</div>
				<div style="display:inline-flex;">
					<form action="ServletCategorySearchList" method="post">
						<input type="submit" value="修正">
						<input type="hidden" name="btn" value="modify">
						<input type="hidden" name="categoryId" value=<%= category.getCategoryId() %>>
						<input type="hidden" name="categoryName" value=<%= category.getCategoryName() %>>
						<input type="hidden" name="categoryKana" value=<%= category.getCategoryKana() %>>
					</form>
				</div>
				<div style="display:inline-flex;">
					<form action="ServletCategorySearchList" method="post">
						<input type="submit" value="削除">
						<input type="hidden" name="btn" value="delete">
						<input type="hidden" name="categoryId" value=<%= category.getCategoryId() %>>
						<input type="hidden" name="categoryName" value=<%= category.getCategoryName() %>>
						<input type="hidden" name="categoryKana" value=<%= category.getCategoryKana() %>>
					</form>
				</div>
			</p>
			
		<%
			}
		%>
		
		<a href="#">1</a>
		<a href="#">2</a>
		<a href="#">3</a>
		<a href="#">4</a>
		<a href="#">5</a>
		<a href="#">6</a>
		<a href="#">7</a>
		<a href="#">8</a>
		<a href="#">9</a>
		<a href="#">10</a>
	
	</body>
</html>