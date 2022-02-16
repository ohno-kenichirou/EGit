<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewCommentInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	String message = (String)request.getAttribute("message");
	NewCommentInfo newComment = null;
	if (session.getAttribute("NewCommentInfo") instanceof NewCommentInfo) {
		newComment = (NewCommentInfo)session.getAttribute("NewCommentInfo");
	}
	UserInfo user = (UserInfo)session.getAttribute("User");
	int threadId = (Integer)request.getAttribute("sendThreadId");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント入力</title>
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
		
		<%
			if (message != null && message.equals("未入力の項目があります")) {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		<div>
			<span>＊</span>マークは入力必須項目
		</div>
		
		<form action="ServletCommentPostConfirm" method="post">
			<div>
				ユーザー名				
			</div>
			<div>
				<%= user.getUserName() %>
			</div>
			
			<div>
				<label for="comment">
					<span>＊</span>コメント内容:
				</label>				
			</div>
			<div>
				<textarea id="comment" name="comment" placeholder="コメント内容" required><% if (newComment != null) %><%= newComment.getComment() %></textarea>
			</div>
			
			<div>
				<input type="submit" value="確認">
			</div>
			<input type="hidden" name="threadId" value="<%= threadId %>">
		</form>
		
		<br>
		
		<a href="ServletThreadDetail?threadId=<%= threadId %>">スレッド詳細画面へ戻る</a>
	
	</body>
</html>