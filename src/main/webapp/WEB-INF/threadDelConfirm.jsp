<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド削除内容確認</title>
	</head>
	<body>
		<header class="flex">
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="#">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
	
		<p>
			削除内容を確認してください
		</p>
		
		<table>
			<tr>
				<th colspan="2">スレッド情報</th>
			</tr>
			<tr>
				<th>スレッドタイトル</th>
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
				<th>スレッド作成日</th>
				<td></td>
			</tr>
			<tr>
				<th>スレッド内容</th>
				<td></td>
			</tr>
		</table>
		
		<br>
		
		<table>
			<tr>
				<th colspan="2">コメント情報</th>
			</tr>
			<tr>
				<th>ユーザー名</th>
				<td></td>
			</tr>
			<tr>
				<th>コメント投稿日</th>
				<td></td>
			</tr>
			<tr>
				<th>コメント内容</th>
				<td></td>
			</tr>
		</table>
		
		<br>
		
		<form action="#">
			<input type="submit" value="削除">
		</form>
		
		<br>
		
		<a href="ServletThreadCreate">スレッド一覧へ戻る</a>
		
		
	</body>
</html>