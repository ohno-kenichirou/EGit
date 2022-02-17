<!-- 
	作成者:高橋　作成日:2022/02/04
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
		<title>ログイン画面</title>
		<link rel="stylesheet" type="text/css" href="css/design.css">
	</head>
	<body>
		<div class="loginForm">
			<%
				if (message != null && message != "") {
			%>
					<div class="caution-text">
						<%= message %>
					</div>
			<%	
				}
			%>
			<form action="ServletLogin" method="post">
			
				<div class="text-center loginHeight">
					<div class="inline-block width">
						<label for="email">
							Email 
						</label>
					</div>
					<div class="inline-block">
						<input type="email" id="email" name="email" required>
					</div>
				</div>
					
				<div class="text-center loginHeight">
					<div class="inline-block width">
						<label for="pass">
							PASS
						</label>
					</div>
					<div class="inline-block">
						<input type="password" id="pass" name="pass" required>
					</div>
				</div>
					
				<div class="text-center loginHeight">
					<input type="submit" value="ログイン">
				</div>
				
				<br>
				<div class="text-center">
					<a href="ServletThreadSearchList">ログインしないでアクセスする</a>
				</div>
			</form>
		</div>
	</body>
</html>