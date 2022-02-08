<!-- 
	処理内容：アカウント削除確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント削除確認</title>
</head>
<body>
	<header class="flex">
		<a href="ServletThreadSearchList">スレッド一覧</a>
		<a href="#">カテゴリー一覧</a>
		<a href="#">アカウント一覧</a>	
		<a href="ServletLogout">ログアウト</a>		
	</header>
	<hr>
	
	<p>対象のユーザーが作成したスレッドとコメントが全て削除されます。<br>本当に削除してもよろしいですか？</p>
	
	<table>
		<tr>
			<th>会員ID</th>
			<td></td>
		</tr>
		<tr>
			<th>メールアドレス</th>
			<td></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td></td>
		</tr>
		<tr>
			<th>生年月日</th>
			<td></td>
		</tr>
		<tr>
			<th>性別</th>
			<td></td>
		</tr>
		<tr>
			<th>管理者権限の有無</th>
			<td></td>
		</tr>
	</table>
	
	<br>
	
	<form action="ServletAccountDelConfirm" method="post">
		<input type="submit" value="削除">
	</form>
	
	<div>
		<a href="ServletAccountSerchList">アカウント一覧画面へ戻る</a>
	</div>
</body>
</html>