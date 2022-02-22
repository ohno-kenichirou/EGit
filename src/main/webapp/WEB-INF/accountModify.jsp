<!-- 
	処理内容:	アカウント修正画面
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.GenderInfo" %>
<%
	String message = (String)request.getAttribute("message");
	UserInfo user = (UserInfo)session.getAttribute("AccountModify");
	ArrayList<GenderInfo> genderList = (ArrayList<GenderInfo>)session.getAttribute("GenderList");
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
	String lift = (String)session.getAttribute("lift");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント修正</title>
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
			<div class="text-center">
				<div class="col-md-12">
					<div class="text-center">
					<% if (message != null && !message.equals("")) { %>
						<div class="caution-text"><%= message %></div>
					<% } %>
					</div>
					<div class="text-center">
						<span class="caution-color">＊</span>マークは必須項目
					<form action="ServletAccountModify" method="post">
						<div class="input-margin">
							<div class="inline-block input-item">
								<label for="userId">
									会員ID:
								</label>				
							</div>
							<div class="input-width inline-block">
								<input type="text" id="userId" name="userId" readonly="readonly" size="11" value="<%= id %>">
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label for="email">
									<span class="caution-color">＊</span>メールアドレス:
								</label>				
							</div>
							<div class="input-width inline-block">
								<input type="email" id="email" name="email" placeholder="メールアドレス入力" maxlength="255" size="62" required value="<%= email %>">
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label for="pass">
									<span class="caution-color">＊</span>パスワード:
								</label>				
							</div>
							<div class="input-width inline-block">
								<input type="password" id="pass" name="pass" placeholder="パスワード入力" minlength="8" maxlength="18" size="18" required value="<%= pass %>">
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label for="userName">
									<span class="caution-color">＊</span>ユーザー名:
								</label>				
							</div>
							<div class="input-width inline-block">
								<input type="text" id="userName" name="userName" placeholder="ユーザー名入力" maxlength="100" size="26" required value="<%= name %>">
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label for="birth">
									<span class="caution-color">＊</span>生年月日:
								</label>				
							</div>
							<div class="input-width inline-block">
								<input type="date" id="birth" name="birth" placeholder="生年月日入力" required value="<%= birth %>">
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label>
									<span class="caution-color">＊</span>性別:
								</label>				
							</div>
							<div class="input-width inline-block">
							<% for (GenderInfo gender : genderList) { 
									if (gender.getGenderId() == genderId) { %>
										<input type="radio" name="genderId" checked="checked" value="<%= gender.getGenderId() %>"><%= gender.getGenderName() %>
							<%		} else { %>
										<input type="radio" name="genderId" value="<%= gender.getGenderId() %>"><%= gender.getGenderName() %>
							<%		}
								} %>
							</div>
						</div>
						<div class="input-margin">
							<div class="inline-block input-item">
								<label>
									管理者権限の有無：
								</label>				
							</div>
							<div class="input-width inline-block">
								<% if (manager == 1) { %>
									<input type="radio" name="manager" value="1" checked="checked">有：管理ユーザー
									<input type="radio" name="manager" value="0">無：会員ユーザー
								<% } else { %>
									<input type="radio" name="manager" value="1">有：管理ユーザー
									<input type="radio" name="manager" value="0" checked="checked">無：会員ユーザー
								<% }%>
							</div>
						</div>
						<% if (errorCount >= 3) { %>
							<div class="input-margin">
								<div class="inline-block input-item">
									<label>
										ロック解除の有無：
									</label>				
								</div>
								<div class="input-width inline-block">
									<% if (lift != null && lift.equals("1")) { %>
										<input type="radio" name="lift" value="1" checked="checked">有：解除する
										<input type="radio" name="lift" value="0">無
									<% } else { %>
										<input type="radio" name="lift" value="1">有：解除する
										<input type="radio" name="lift" value="0" checked="checked">無
									<% } %>
								</div>
							</div>
						<% }%>
						<input type="hidden" name="errorCount" value="<%= errorCount %>">
						<input type="hidden" name="fromPage" value="accountModify">
						<div class="input-margin">
							<input type="submit" class="default width5" value="確認">
						</div>
					</form>
					</div>
					<br>	
					<br>
					<div class="text-center">
						<a href="ServletAccountSearchList">アカウント一覧へ戻る</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>