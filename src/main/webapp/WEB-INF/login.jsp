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
		<link rel="stylesheet" type="text.css" href="../cssLogout.css">
	</head>
	<body>
		<%
			if (message != null && message != "") {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		<form action="ServletLogin" method="post">
			<table>
				<tr>
					<th>
						<label for="email">
							Email 
						</label>
					</th>
					<td>
						<input type="email" id="email" name="email" required>
					</td>
				</tr>
				<tr>
					<th>
						<label for="pass">
							PASS
						</label>
					</th>
					<td>
						<input type="password" id="pass" name="pass" required>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="ログイン">
					</td>
				</tr>
			</table>
			
			<a href="ServletLogin">ログインしないでアクセスする</a>
		</form>
	</body>
</html>