<!-- 
	処理内容：アカウント修正確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント修正確認</title>
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
			<th></th>
			<td>修正前</td>
			<td>修正後</td>
		</tr>
		<tr>
		<tr>
			<th>会員ID</th>
			<td colspan="2"></td>
		</tr>
		<tr>
			<th>メールアドレス</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>パスワード</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>生年月日</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>性別</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>管理者権限の有無</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>ロック解除の有無</th>
			<td>-</td>
			<td></td>
		</tr>
	</table>
	
	<br>
	
	<form action="ServletAccountModifyConfirm" method="post">
		<input type="submit" value="修正">
	</form>
	
	<div>
		<a href="ServletAccountModify">アカウント修正画面へ戻る</a>
	</div>
</body>
</html>