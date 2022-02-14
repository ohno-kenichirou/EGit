<!-- 
	処理内容：カテゴリー追加確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.CategoryInfo" %>
<%
	String message = (String)request.getAttribute("message");
	UserInfo user = (UserInfo)session.getAttribute("User");
	String userName = "";
	if (user != null) {
		userName = user.getUserName();
	}
	CategoryInfo category = (CategoryInfo)session.getAttribute("CategoryAdd");
	String name = "";
	String kana = "";
	if (category != null) {
		name = category.getCategoryName();
		kana = category.getCategoryKana();
	}
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
			<td><%= name %></td>
		</tr>
		<tr>
			<th>カテゴリー名(カナ)</th>
			<td><%= kana %></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td><%= userName %></td>
		</tr>
	</table>
	
	<br>
	
	<%
		if (message != null && !message.equals("")) {
	%>
			<div>
				<%= message %>
			</div>
	<%	
		}
	%>
	
	<form action="ServletCategoryAddConfirm" method="post">
		<input type="submit" value="追加">
	</form>
	
	<div>
		<a href="ServletCategoryAdd">カテゴリー追加画面へ戻る</a>
	</div>
</body>
</html>