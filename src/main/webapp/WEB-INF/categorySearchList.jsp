<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String searchCategoryWord = (String)request.getAttribute("searchCategoryWord");
	if (searchCategoryWord == null) {
		searchCategoryWord = "カテゴリー名入力";
	}
	
	String message = (String)request.getAttribute("message");
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
			<a href="#">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		
		<form action="ServletCategorySearchList" method="post">
			<label>
				<span>カテゴリー名:</span>
			</label>			
			<input type="text" id="searchCategoryWord" name="searchCategoryWord" placeholder="<%= searchCategoryWord %>">
			<input type="radio" name="searchMethod" value="部分一致" checked="checked">部分一致
			<input type="radio" name="searchMethod" value="完全一致">完全一致
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
		
		<form action="#" method="post">
			<input type="submit" value="カテゴリー追加">
		</form>
		
		<ul>
			<li>
				<a href="#"></a>
				<br>
				<form action="#">
					<input type="submit" value="修正">
					<input type="hidden" name="update" value="modify">
				</form>
				<form action="#">
					<input type="submit" value="削除">
					<input type="hidden" name="update" value="delete">
				</form>
			</li>
		</ul>
		
		<br>
		
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