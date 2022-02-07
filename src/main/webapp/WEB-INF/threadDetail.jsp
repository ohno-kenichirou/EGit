<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String message = (String)request.getAttribute("message");
%>    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド詳細</title>
	</head>
	<body>
		<header class="flex">
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="#">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		<%
			if (message != null && message.equals("コメントが削除されました")) {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		
		<form action="#" method="post">
			<input type="submit" value="コメントする">
		</form>
		
		
		<%
			if (message != null && message.equals("コメント数が50件以上のため、コメントすることができません")) {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
	</body>
</html>