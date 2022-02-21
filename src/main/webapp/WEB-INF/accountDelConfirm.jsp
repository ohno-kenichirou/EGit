<!-- 
	処理内容：アカウント削除確認画面

	作成者:大野賢一朗 作成日:2022/02/10
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.GenderInfo" %>
<%
	String message = (String)request.getAttribute("message");
	UserInfo user = (UserInfo)session.getAttribute("AccountDel");
	ArrayList<GenderInfo> genderList = (ArrayList<GenderInfo>)session.getAttribute("GenderList");
	String id = "";
	String email = "";
	String name = "";
	Date birth = null;
	int genderId = 0;
	int manager = 0;
	if (user != null) {
		id = user.getUserId();
		email = user.getEmail();
		name = user.getUserName();
		birth = user.getBirth();
		genderId = user.getGenderId();
		manager = user.getManager();
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント削除確認</title>
	<link rel="shortcut icon" href="img/bulletin_board.ico">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/main.min.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/style.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/design.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/design2.css" media="all">
</head>
<body>
	<div>
		<jsp:include page="header.jsp" flush="true" />
		<div class="mb-45">
			<div class="col-md-12">
				<div class="text-center">
					
					<p>対象のユーザーが作成したスレッドとコメントが全て削除されます。<br>本当に削除してもよろしいですか？</p>
					<div class="inline-block">
					<table>
						<tr>
							<th>会員ID</th>
							<td><%= id %></td>
						</tr>
						<tr>
							<th>メールアドレス</th>
							<td><%= email %></td>
						</tr>
						<tr>
							<th>ユーザー名</th>
							<td><%= name %></td>
						</tr>
						<tr>
							<th>生年月日</th>
							<td><%= birth %></td>
						</tr>
						<tr>
							<th>性別</th>
							<td>
								<%
									if (genderList != null) { 
										for (GenderInfo gender : genderList) {
											if (gender.getGenderId() == genderId) {
								%>
												<%= gender.getGenderName() %>
								<% 
											}
										}
									} 
								%>
							</td>
						</tr>
						<tr>
							<th>管理者権限の有無</th>
							<td>
								<% if (manager == 0) { %>
									無
								<% } else { %>
									有
								<% } %>
							</td>
						</tr>
					</table>
					</div>
					<br>
					
					<% if (message != null && !message.equals("")) { %>
						<div class="caution-text"><%= message %></div>
					<% } %>
					
					<form action="ServletAccountDelConfirm" method="post">
						<input type="submit" class="delete width5" value="削除">
					</form>
					
					<div>
						<a href="ServletAccountSearchList">アカウント一覧画面へ戻る</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>