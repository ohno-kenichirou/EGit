<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.UserInfo" %>
<% 
	UserInfo user = (UserInfo)session.getAttribute("User");
%>
		<header>
			<div>
				<div class="navigationLink"><a href="ServletThreadSearchList">スレッド一覧</a></div>
				
				<%
					if (user != null && user.getManager() == 1) {
				%>
						<div class="navigationLink"><a href="ServletCategorySearchList">カテゴリー一覧</a></div>
						<div class="navigationLink"><a href="ServletAccountSearchList">アカウント一覧</a></div>
				<%		
					}
					if (user != null) {
				%>
						<div class="navigationLink"><a href="ServletLogout">ログアウト</a></div>
				<%
					} else {
				%>
						<div class="navigationLink"><a href="ServletLogin">ログイン</a></div>
				<%
					}
				%>		
			</div>	
		</header>
		<hr>