<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド作成内容確認</title>
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
				<th>タイトル</th>
				<td></td>
			</tr>
			<tr>
				<th>カテゴリー</th>
				<td></td>
			</tr>
			<tr>
				<th>ユーザー名</th>
				<td></td>
			</tr>
			<tr>
				<th>スレッド内容</th>
				<td></td>
			</tr>
		</table>
		
		<br>
		
		<form action="#">
			<input type="submit" value="作成">
		</form>
		
		<br>
		
		<a href="ServletThreadCreate">スレッド作成画面へ戻る</a>
	</body>
</html>