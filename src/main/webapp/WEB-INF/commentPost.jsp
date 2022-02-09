<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewCommentInfo" %>
<%
	String message = (String)request.getAttribute("message");
	String comment = "コメント内容";

	NewCommentInfo newComment = (NewCommentInfo)request.getAttribute("sendNewCommentInfo");
	if (newComment != null) {
		comment = newComment.getComment();
	}
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
			<a href="ServletCategorySearchList">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
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
		
		<form action="#" method="post">
			<div>
				ユーザー名				
			</div>
			<div>
				
			</div>
			
			<div>
				<label for="comment">
					<span>＊</span>コメント内容:
				</label>				
			</div>
			<div>
				<textarea id="comment" name="comment" placeholder="<%= comment %>" required></textarea>
			</div>
			
			<div>
				<input type="submit" value="確認">
			</div>
		</form>
		
		<br>
		
		<a href="ServletThreadDetail">スレッド詳細画面へ戻る</a>
	
	</body>
</html>