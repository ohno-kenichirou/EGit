<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewThreadInfo" %>
<%
	NewThreadInfo newThread = null;
	if (request.getAttribute("sendNewThreadInfo") instanceof NewThreadInfo) {
		newThread = (NewThreadInfo)request.getAttribute("sendNewThreadInfo");
	}

	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド作成内容確認</title>
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="ServletCategorySearchList">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		
		<p>入力内容を確認して下さい</p>
		
		<table>
			<tr>
				<th>タイトル</th>
				<td><%= newThread.getTitle() %></td>
			</tr>
			<tr>
				<th>カテゴリー</th>
				<td><%= newThread.getCategory() %></td>
			</tr>
			<tr>
				<th>ユーザー名</th>
				<td><%= newThread.getUserName() %></td>
			</tr>
			<tr>
				<th>スレッド内容</th>
				<td><%= newThread.getComment() %></td>
			</tr>
		</table>
		
		<br>
		
		<form action="ServletThreadSearchList" method="post">
			<input type="submit" value="作成">
		</form>
		
		<br>
		
		<a href="ServletThreadCreate">スレッド作成画面へ戻る</a>
	</body>
</html>