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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント修正</title>
</head>
<body>
	<header class="flex">
		<a href="ServletThreadSearchList">スレッド一覧</a>
		<a href="#">カテゴリー一覧</a>
		<a href="#">アカウント一覧</a>	
		<a href="ServletLogout">ログアウト</a>		
	</header>
	<hr>
	
	<% if (message != null && !message.equals("")) { %>
		<div><%= message %></div>
	<% } %>
	
	<div>
		<span>＊</span>マークは入力必須項目
	</div>
	<form action="ServletAccountModify" method="post">
		<div>
			<label for="userId">
				会員ID:
			</label>				
		</div>
		<div>
			<input type="text" id="userId" name="userId" disabled="disabled" value="<%= id %>">
		</div>
		<div>
			<label for="email">
				<span>＊</span>メールアドレス:
			</label>				
		</div>
		<div>
			<input type="email" id="email" name="email" placeholder="メールアドレス入力" maxlength="255" required value="<%= email %>">
		</div>
		<div>
			<label for="pass">
				<span>＊</span>パスワード:
			</label>				
		</div>
		<div>
			<input type="password" id="pass" name="pass" placeholder="パスワード入力" maxlength="255" required value="<%= pass %>">
		</div>
		<div>
			<label for="userName">
				<span>＊</span>ユーザー名:
			</label>				
		</div>
		<div>
			<input type="text" id=userName name="userName" placeholder="ユーザー名入力" maxlength="100" required value="<%= name %>">
		</div>
		<div>
			<label for="birth">
				<span>＊</span>生年月日:
			</label>				
		</div>
		<div>
			<input type="date" id=birth name="birth" placeholder="生年月日入力" required value="<%= birth %>">
		</div>
		<div>
			<label>
				<span>＊</span>性別:
			</label>				
		</div>
		<div>
			<% for (GenderInfo gender : genderList) { 
					if (gender.getGenderId() == genderId) { %>
						<input type="radio" name="genderId" checked="checked" value="<%= gender.getGenderId() %>"><%= gender.getGenderName() %>
			<%		} else { %>
						<input type="radio" name="genderId" value="<%= gender.getGenderId() %>"><%= gender.getGenderName() %>
			<%		}
				} %>
		</div>
		<div>
			<label>
				管理者権限の有無：
			</label>				
		</div>
		<div>
			<% if (manager == 1) { %>
				<input type="radio" name="manager" value="1" checked="checked">有：管理ユーザー
				<input type="radio" name="manager" value="0">無：会員ユーザー
			<% } else { %>
				<input type="radio" name="manager" value="1">有：管理ユーザー
				<input type="radio" name="manager" value="0" checked="checked">無：会員ユーザー
			<% }%>
		</div>
		<% if (errorCount >= 3) { %>
			<div>
				<label>
					ロック解除の有無：
				</label>				
			</div>
			<div>
				<input type="radio" name="lift" value="1">有：解除する
				<input type="radio" name="lift" value="0" checked="checked">無
			</div>
		<% }%>
		<input type="hidden" name="errorCount" value="<%= errorCount %>">
		<div>
			<input type="submit" value="確認">
		</div>
	</form>
	<div>
		<a href="ServletAccountSearchList">アカウント一覧へ戻る</a>
	</div>
</body>
</html>