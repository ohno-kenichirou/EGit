<!-- 
	処理内容:	アカウント一覧画面
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.GenderInfo" %>
<%
	String message = (String)request.getAttribute("message");
	String searchName = (String)session.getAttribute("searchUserName");
	String selectMatch = (String)session.getAttribute("selectMatch");
	String pageNo = (String)request.getAttribute("accountPageNo"); 
	int totalNum = (int)request.getAttribute("totalNum"); 
	ArrayList<UserInfo> accountList = (ArrayList<UserInfo>)session.getAttribute("AccountSearchList");
	ArrayList<GenderInfo> genderList = (ArrayList<GenderInfo>)session.getAttribute("GenderList");
	ArrayList<UserInfo> userList = (ArrayList<UserInfo>)session.getAttribute("UserList");
	if (searchName == null) {
		searchName ="";
	}
	int pageNum = 0;
	if (pageNo != null && !pageNo.equals("")) {
		pageNum = Integer.parseInt(pageNo);
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント一覧</title>
</head>
<body>
	<header class="flex">
		<a href="ServletThreadSearchList">スレッド一覧</a>
		<a href="#">カテゴリー一覧</a>
		<a href="#">アカウント一覧</a>	
		<a href="ServletLogout">ログアウト</a>		
	</header>
	<hr>
	<form action="ServletAccountSearchList" method="post">
		<p>
			<span>ユーザー名:</span>
			<input type="text" name="searchUserName" placeholder="ユーザー名入力" maxlength="100" value="<%= searchName %>">
			<% if (selectMatch != null && selectMatch != "" && selectMatch.equals("perfect")) { %>
				<input type="radio" name="selectMatch" value="partial">部分一致
				<input type="radio" name="selectMatch" value="perfect" checked="checked">完全一致
			<% } else { %>
				<input type="radio" name="selectMatch" value="partial" checked="checked">部分一致
				<input type="radio" name="selectMatch" value="perfect">完全一致
			<% } %>
			<input type="hidden" name="btn" value="search">
			<input type="submit" value="検索">
		</p>
	</form>
	<hr>
	<% if (message != null && message != "") { %>
		<div><%= message %></div>
	<% } %>
	<form action="ServletAccountSearchList" method="post">
		<input type="hidden" name="btn" value="register">
		<input type="submit" value="アカウント登録">
	</form>
	<table>
		<tr>
			<th></th>
			<th></th>
			<th>会員ID</th>
			<th>メールアドレス</th>
			<th>ユーザー名</th>
			<th>生年月日</th>
			<th>性別</th>
			<th>管理者権限の有無</th>
			<th>ロック</th>
			<th>登録者</th>
			<th>登録日</th>
			<th>更新者</th>
			<th>更新日</th>
		</tr>
		<% for (UserInfo account : accountList) { %>
			<tr>
				<td>
					<form action="ServletAccountSearchList" method="post">
						<input type="hidden" name="userId"		value="<%= account.getUserId() %>">
						<input type="hidden" name="email"		value="<%= account.getEmail() %>">
						<input type="hidden" name="userName"	value="<%= account.getUserName() %>">
						<input type="hidden" name="birth"		value="<%= account.getBirth() %>">
						<input type="hidden" name="genderId"	value="<%= account.getGenderId() %>">
						<input type="hidden" name="manager"		value="<%= account.getManager() %>">
						<input type="hidden" name="errorCount"	value="<%= account.getErrorCount() %>">
						<input type="hidden" name="btn"			value="modify">
						<input type="submit" value="修正">
					</form>
				</td>
				<td>
					<form action="ServletAccountSearchList" method="post">
						<input type="hidden" name="userId"		value="<%= account.getUserId() %>">
						<input type="hidden" name="email"		value="<%= account.getEmail() %>">
						<input type="hidden" name="userName"	value="<%= account.getUserName() %>">
						<input type="hidden" name="birth"		value="<%= account.getBirth() %>">
						<input type="hidden" name="genderId"	value="<%= account.getGenderId() %>">
						<input type="hidden" name="manager"		value="<%= account.getManager() %>">
						<input type="hidden" name="errorCount"	value="<%= account.getErrorCount() %>">
						<input type="hidden" name="btn"			value="delete">
						<input type="submit" value="削除">
					</form>
				</td>
				<td><%= account.getUserId() %></td>
				<td><%= account.getEmail() %></td>
				<td><%= account.getUserName() %></td>
				<td><%= sdf.format(account.getBirth()) %></td>
				<td>
					<% for (GenderInfo gender : genderList) { 
						if (gender.getGenderId() == account.getGenderId()) { %>
							<%= gender.getGenderName() %>
					<%	}
					} %>
				</td>
				<td>
					<% if (account.getManager() == 1) { %>
						有
					<% } else { %>
						無
					<% } %>
				</td>
				<td><%= account.getErrorCount() %></td>
				<td>
					<% for (UserInfo user : userList) { 
						if (user.getUserId().equals(account.getDispInsUserId())) { %>
							<%= user.getUserName() %>
						<% }
					} %>
				</td>
				<td><%= sdf.format(account.getDispInsDate()) %></td>
				<td>
					<% for (UserInfo user : userList) { 
						if (user.getUserId().equals(account.getDispUpdUserId())) { %>
							<%= user.getUserName() %>
						<% }
					} %>
				</td>
				<td>
					<% if (account.getDispUpdDate() != null) { %>
						<%= sdf.format(account.getDispUpdDate()) %>
					<% } %>
				</td>
			</tr>
		</table>
	<% } %>
	<div>
	<% for (int i = 1; i <= totalNum; i++) {
		if (i == pageNum) { %>
			<span><%= i %></span>
		<% } else { %>
			<a href="ServletAccountSearchList?accountPageNo=<%= i %>"><%= i %></a>
		<% }
	} %>
	</div>
</body>
</html>