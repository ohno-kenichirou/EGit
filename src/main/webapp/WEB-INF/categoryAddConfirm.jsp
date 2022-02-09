<!-- 
	処理内容：カテゴリー追加確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.CategoryAddInfo" %>
<%
	UserInfo user = (UserInfo)session.getAttribute("User");
	CategoryAddInfo category = (CategoryAddInfo)session.getAttribute("CategoryAdd");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリー追加確認</title>
</head>
<body>
	<header class="flex">
		<a href="ServletThreadSearchList">スレッド一覧</a>
		<a href="#">カテゴリー一覧</a>
		<a href="#">アカウント一覧</a>	
		<a href="ServletLogout">ログアウト</a>		
	</header>
	<hr>
	
	<p>入力内容を確認して下さい</p>
	
	<table>
		<tr>
			<th>カテゴリー名</th>
			<td><%= category.getCategoryName() %></td>
		</tr>
		<tr>
			<th>カテゴリー名(カナ)</th>
			<td><%= category.getCategoryKana() %></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td><%= user.getUserName() %></td>
		</tr>
	</table>
	
	<br>
	
	<form action="ServletCategoryAddConfirm" method="post">
		<input type="submit" value="追加">
	</form>
	
	<div>
		<a href="ServletCategoryAdd">カテゴリー追加画面へ戻る</a>
	</div>
</body>
</html>