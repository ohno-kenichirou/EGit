<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント削除内容確認</title>
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
				<th colspan="2">
					コメント情報
				</th>
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
		
		<form action="#" method="post">
			<input type="submit" value="削除">
		</form>
		
		<br>
		
		<a href="ServletThreadDetail">スレッド詳細画面へ戻る</a>
		
	</body>
</html>