<!-- 
	処理内容：カテゴリー修正確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリー修正確認</title>
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
			<td></td>
		</tr>
		<tr>
			<th>カテゴリー名(カナ)</th>
			<td></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td></td>
		</tr>
	</table>
	
	<br>
	
	<form action="ServletCategoryModifyConfirm" method="post">
		<input type="submit" value="修正">
	</form>
	
	<div>
		<a href="ServletCategoryModify">カテゴリー修正画面へ戻る</a>
	</div>
</body>
</html>