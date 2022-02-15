<!-- 
	処理内容：アカウント登録確認画面

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
	UserInfo acount = (UserInfo)session.getAttribute("accountyRegister");
	UserInfo user = (UserInfo)session.getAttribute("User");
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
	if (acount != null) {
		id = acount.getUserId();
		email = acount.getEmail();
		pass = acount.getPass();
		name = acount.getUserName();
		birth = acount.getBirth();
		genderId = acount.getGenderId();
		manager = acount.getManager();
		errorCount = acount.getErrorCount();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント登録確認</title>
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
			<th>会員ID</th>
			<td><%= id %></td>
		</tr>
		<tr>
			<th>メールアドレス</th>
			<td><%= email %></td>
		</tr>
		<tr>
			<th>パスワード</th>
			<td><%= pass %></td>
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
			<%	for (GenderInfo gender : genderList) { 
					if (gender.getGenderId() == genderId) {	%>
						<td><%= gender.getGenderName() %></td>
			<%		}
				}	%>
		</tr>
		<tr>
			<th>管理者権限の有無</th>
			<td>
				<% if (manager == 1) { %>
					有
				<% } else { %>
					無
				<% } %>
			</td>
		</tr>
		<tr>
			<th>登録者</th>
			<td><%= user.getUserName() %></td>
		</tr>
	</table>
	
	<br>
	
	<form action="ServletAccountRegisterConfirm" method="post">
		<input type="submit" value="登録">
	</form>
	
	<div>
		<a href="ServletAccountRegister">アカウント登録画面へ戻る</a>
	</div>
</body>
</html>