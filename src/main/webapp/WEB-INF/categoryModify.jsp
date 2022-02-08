<!-- 
	内容:	カテゴリー修正画面
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
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
<title>カテゴリー修正</title>
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
		if (message != null && !message.equals("")) {
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
	<form action="ServletCategoryModify" method="post">
		<div>
			<label for="categoryName">
				<span>＊</span>カテゴリー名:
			</label>				
		</div>
		<div>
			<input type="text" id="categoryName" name="categoryName" placeholder="カテゴリー名入力" required>
		</div>
		<div>
			<label for="categoryKana">
				<span>＊</span>カテゴリー名(カナ):
			</label>				
		</div>
		<div>
			<input type="text" id="categoryKana" name="categoryKana" placeholder="カテゴリー名(カナ)入力" required>
		</div>
		<div>
			<input type="submit" value="確認">
		</div>
	</form>
	<div>
		<a href="ServletCategorySearchList">カテゴリー一覧へ戻る</a>
	</div>
</body>
</html>