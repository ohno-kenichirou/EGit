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
	<link rel="shortcut icon" href="img/bulletin_board.ico">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
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
			<div>
				<div class="col-md-12">
					<div class="text-center">
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
								<input type="submit"  class="search-margin default width5" value="検索">
							</p>
						</form>
					</div>
					<hr>
					<% if (message != null && message != "") { %>
						<div class="text-center"><%= message %></div>
					<% } %>
					<div class="text-center">
						<form action="ServletAccountSearchList" method="post">
							<input type="hidden" name="btn" value="register">
							<input type="submit" class="add width12" value="アカウント登録">
						</form>
					</div>
					<div class="text-center">
						<table>
								<tr>
									<th class="wd-btnMod"></th>
									<th class="wd-btnDel"></th>
									<th class="wd-userId">会員ID</th>
									<th class="wd-email">メールアドレス</th>
									<th class="wd-userName">ユーザー名</th>
									<th class="wd-birth">生年月日</th>
									<th class="wd-gender">性別</th>
									<th class="wd-manager">管理者権限の有無</th>
									<th class="wd-lock">ロック</th>
									<th class="wd-insName">登録者</th>
									<th class="wd-insDate">登録日</th>
									<th class="wd-updName">更新者</th>
									<th class="wd-updDate">更新日</th>
								</tr>
								<% for (UserInfo account : accountList) { %>
									<tr>
										<td class="wd-btnMod">
											<form action="ServletAccountSearchList" method="post">
												<input type="hidden" name="userId"		value="<%= account.getUserId() %>">
												<input type="hidden" name="email"		value="<%= account.getEmail() %>">
												<input type="hidden" name="userName"	value="<%= account.getUserName() %>">
												<input type="hidden" name="birth"		value="<%= account.getBirth() %>">
												<input type="hidden" name="genderId"	value="<%= account.getGenderId() %>">
												<input type="hidden" name="manager"		value="<%= account.getManager() %>">
												<input type="hidden" name="errorCount"	value="<%= account.getErrorCount() %>">
												<input type="hidden" name="btn"			value="modify">
												<input type="submit" class="modify width5" value="修正">
											</form>
										</td>
										<td class="wd-btnDel">
											<form action="ServletAccountSearchList" method="post">
												<input type="hidden" name="userId"		value="<%= account.getUserId() %>">
												<input type="hidden" name="email"		value="<%= account.getEmail() %>">
												<input type="hidden" name="userName"	value="<%= account.getUserName() %>">
												<input type="hidden" name="birth"		value="<%= account.getBirth() %>">
												<input type="hidden" name="genderId"	value="<%= account.getGenderId() %>">
												<input type="hidden" name="manager"		value="<%= account.getManager() %>">
												<input type="hidden" name="errorCount"	value="<%= account.getErrorCount() %>">
												<input type="hidden" name="btn"			value="delete">
												<input type="submit" class="delete width5" value="削除">
											</form>
										</td>
										<td class="wd-userId"><%= account.getUserId() %></td>
										<td class="wd-email"><%= account.getEmail() %></td>
										<td class="wd-userName"><%= account.getUserName() %></td>
										<td class="wd-birth"><%= sdf.format(account.getBirth()) %></td>
										<td class="wd-gender">
											<% for (GenderInfo gender : genderList) { 
												if (gender.getGenderId() == account.getGenderId()) { %>
													<%= gender.getGenderName() %>
											<%	}
											} %>
										</td>
										<td class="wd-manager">
											<% if (account.getManager() == 1) { %>
												有
											<% } else { %>
												無
											<% } %>
										</td>
										<td class="wd-lock">
											<% if (account.getErrorCount() >= 3) { %>
												<span class="fa fa-lock red-color"></span>
											<% } else { %>
												<span class="fa fa-unlock blue-color"></span>
											<% } %>
										</td>
										<td class="wd-insName">
											<% for (UserInfo user : userList) { 
												if (user.getUserId().equals(account.getDispInsUserId())) { %>
													<%= user.getUserName() %>
												<% }
											} %>
										</td>
										<td class="wd-insDate"><%= sdf.format(account.getDispInsDate()) %></td>
										<td class="wd-updName">
											<% for (UserInfo user : userList) { 
												if (user.getUserId().equals(account.getDispUpdUserId())) { %>
													<%= user.getUserName() %>
												<% }
											} %>
										</td>
										<td class="wd-updDate">
											<% if (account.getDispUpdDate() != null) { %>
												<%= sdf.format(account.getDispUpdDate()) %>
											<% } %>
										</td>
									</tr>
								<% } %>
						</table>
					</div>
					<br>
					<div class="text-center">
					<% for (int i = 1; i <= totalNum; i++) {
						if (i == pageNum) { %>
							<span class="pagination-margin"><%= i %></span>
						<% } else { %>
							<span class="pagination-margin"><a href="ServletAccountSearchList?accountPageNo=<%= i %>"><%= i %></a></span>
						<% }
					} %>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>