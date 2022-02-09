<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewThreadInfo" %>
<%
	String message = (String)request.getAttribute("message");
	String title = "タイトル入力";
	String comment = "スレッド内容";
	if (request.getAttribute("sendNewThreadInfo") instanceof NewThreadInfo) {
		NewThreadInfo newThread = (NewThreadInfo)request.getAttribute("sendNewThreadInfo");
		title = newThread.getTitle();
		comment = newThread.getComment();
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド作成</title>
	</head>
	<body>
		<header class="flex">
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
		
		<form action="ServletThreadCreateConfirm" method="post">
			<div>
				<label for="title">
					<span>＊</span>タイトル:
				</label>				
			</div>
			<div>
				<input type="text" id="title" name="title" placeholder="<%= title %>" required>
			</div>
			
			<div>
				<label for="category">
					<span>＊</span>カテゴリー:
				</label>				
			</div>
			<div>
				<select id="category" name="category">
					<option value="カテゴリー">カテゴリー</option>
				</select>
			</div>
			<div>
				<label for="comment">
					<span>＊</span>スレッド内容:
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
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
		
	</body>
</html>