<!-- 
	処理内容：アカウント修正確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.GenderInfo" %>
<%
	String message = (String)request.getAttribute("message");
	UserInfo user_before = (UserInfo)session.getAttribute("AccountBefore");
	UserInfo user = (UserInfo)session.getAttribute("AccountModify");
	ArrayList<GenderInfo> genderList = (ArrayList<GenderInfo>)session.getAttribute("GenderList");
	String lift = (String)request.getParameter("lift");
	String id = "";
	String email = "";
	String pass = "";
	String name = "";
	Date birth = null;
	int genderId = 0;
	int manager = 0;
	int errorCount = 0;
	if (user != null) {
		id = user.getUserId();
		email = user.getEmail();
		pass = user.getPass();
		name = user.getUserName();
		birth = user.getBirth();
		genderId = user.getGenderId();
		manager = user.getManager();
		errorCount = user.getErrorCount();
	}
	String id_before = "";
	String email_before = "";
	String name_before = "";
	Date birth_before = null;
	int genderId_before = 0;
	int manager_before = 0;
	if (user_before != null) {
		id_before = user_before.getUserId();
		email_before = user_before.getEmail();
		name_before = user_before.getUserName();
		birth_before = user_before.getBirth();
		genderId_before = user_before.getGenderId();
		manager_before = user_before.getManager();
	}
%>
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
			<td>修正内容</td>
		</tr>
		<tr>
			<th>会員ID</th>
			<td colspan="2"><%= id %></td>
		</tr>
		<tr>
			<th>メールアドレス</th>
			<td><%= email_before %></td>
			<td><%= email %></td>
		</tr>
		<tr>
			<th>パスワード</th>
			<td>-</td>
			<td><%= pass %></td>
		</tr>
		<tr>
			<th>ユーザー名</th>
			<td><%= name_before %></td>
			<td><%= name %></td>
		</tr>
		<tr>
			<th>生年月日</th>
			<td><%= birth_before %></td>
			<td><%= birth %></td>
		</tr>
		<tr>
			<th>性別</th>
			<%	for (GenderInfo gender : genderList) { 
					if (gender.getGenderId() == genderId_before) {	%>
						<td><%= gender.getGenderName() %></td>
			<%		}
				}	%>
			<%	for (GenderInfo gender : genderList) { 
					if (gender.getGenderId() == genderId) {	%>
						<td><%= gender.getGenderName() %></td>
			<%		}
				}	%>
		</tr>
		<tr>
			<th>管理者権限の有無</th>
			<td>
				<% if (manager_before == 1) { %>
					有
				<% } else { %>
					無
				<% } %>
			</td>
			<td>
				<% if (manager == 1) { %>
					有
				<% } else { %>
					無
				<% } %>
			</td>
		</tr>
		<% if (errorCount >= 3) { %>
		<tr>
			<th>ロック解除の有無</th>
			<td>-</td>
			<td>
				<% if (Integer.parseInt(lift) == 1) { %>
					有
				<% } else { %>
					無
				<% } %>
			</td>
		</tr>
		<% }%>
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