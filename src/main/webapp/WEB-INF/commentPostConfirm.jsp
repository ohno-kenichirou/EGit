<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewCommentInfo" %>
<%
	NewCommentInfo newComment = null;
	if (request.getAttribute("sendNewCommentInfo") instanceof NewCommentInfo) {
		newComment = (NewCommentInfo)request.getAttribute("sendNewCommentInfo");
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント投稿内容確認</title>
	</head>
	<body>
		<header class="flex">
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="ServletCategorySearchList">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		
		<p>
			入力内容を確認してください
		</p>
		
		<table>
			<tr>
				<th>ユーザー名</th>
				<td><%= newComment.getUserName() %></td>
			</tr>
			<tr>
				<th>コメント内容</th>
				<td><%= newComment.getComment() %></td>
			</tr>
			
		</table>
		
		<br>
		
		<form action="ServletThreadDetail" method="post">
			<input type="submit" value="投稿">
		</form>
		
		<br>
		
		<a href="ServletCommentPost?newComment">コメント作成画面へ戻る</a>
		
	</body>
</html>