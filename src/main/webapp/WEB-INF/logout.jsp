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
		<link rel="stylesheet" type="text/css" href="css/design.css">
		<link rel="shortcut icon" href="img/bulletin_board.ico">
	</head>
	<body>
		<div class="logoutForm">
		
			<p class="text-center">ログアウトします。よろしいですか？</p>
			
			<div class="text-center">
				<div class="inline-block button-margin">
					<form action="ServletLogout" method="post">
						<input class="default width5" type="submit" value="はい">
						<input type="hidden" name="logout" value="yes">
					</form>
				</div>
				<div class="inline-block button-margin">
					<form action="ServletLogout" method="post">
						<input class="default width5" type="submit" value="いいえ">
						<input type="hidden" name="logout" value="no">
					</form>
				</div>
			</div>
		
		</div>
		
	</body>
</html>