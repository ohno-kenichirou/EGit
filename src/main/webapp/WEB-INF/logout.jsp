<!-- 
	作成者:高橋 作成日:2022/02/04
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ログアウト画面</title>
		<link rel="stylesheet" type="text.css" href="../css.css">
	</head>
	<body>
		<p>ログアウトします。よろしいですか？</p>
		
		<div>
			<div>
				<form action="#" method="post">
					<input type="submit" value="はい">
					<input type="hidden" name="logout" value="yes">
				</form>
			</div>
			<div>
				<form action="#" method="post">
					<input type="submit" value="いいえ">
					<input type="hidden" name="logout" value="no">
				</form>
			</div>
		</div>
		
	</body>
</html>