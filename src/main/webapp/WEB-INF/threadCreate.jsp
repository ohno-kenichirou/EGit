<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewThreadInfo" %>
<%@ page import="bulletinBoard.CategoryNameDisp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	String message = (String)request.getAttribute("message");
	String title = "タイトル入力";
	String comment = "スレッド内容";
	
	NewThreadInfo newThread = null;
	if (session.getAttribute("NewThreadInfo") instanceof NewThreadInfo) {
		newThread = (NewThreadInfo)session.getAttribute("NewThreadInfo");
	}
	
	ArrayList<CategoryNameDisp> categoryList = null;
	if (session.getAttribute("CategoryList") instanceof ArrayList) {
		categoryList = (ArrayList<CategoryNameDisp>)session.getAttribute("CategoryList");
	}	
	UserInfo user = (UserInfo)session.getAttribute("User");
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド作成</title>
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			
			<%
				if (user != null && user.getManager() == 1) {
			%>
					<a href="ServletCategorySearchList">カテゴリー一覧</a>
					<a href="ServletAccountSearchList">アカウント一覧</a>
			<%		
				}
				if (user != null) {
			%>
					<a href="ServletLogout">ログアウト</a>	
			<%
				} else {
			%>
					<a href="ServletLogin">ログイン</a>	
			<%
				}
			%>		
				
		</header>
		<hr>
		
		<%
			if (message != null && message.equals("未入力の項目があります")) {
		%>
				<div>
					<%= message %>
				</div>
		<%	
			}
		%>
		
		<div>
			<span>＊</span>マークは入力必須項目
		</div>
		
		<form action="ServletThreadCreateConfirm" method="post">
			<div>
				<label for="title">
					<span>＊</span>タイトル:
				</label>				
			</div>
			<div>
				<input type="text" id="title" name="title" placeholder="<%= title %>" required <%
																									if (newThread != null) {
																								%>
																										value="<%= newThread.getTitle() %>"																								
																								<%
																									}
																								%>>
			</div>
			
			<div>
				<label for="category">
					<span>＊</span>カテゴリー:
				</label>				
			</div>
						
			<div>
				<select id="category" name="categoryId">
					<%
						if (categoryList != null && categoryList.size() > 0) {
							for (CategoryNameDisp category : categoryList) {
								
					%>
								<option value="<%= category.getCategoryId() %>"><%= category.getCategoryName() %></option>
					<%			
							}
						}
					%>
				</select>
			</div>
			<div>
				<label for="comment">
					<span>＊</span>スレッド内容:
				</label>				
			</div>
			<div>
				<textarea id="comment" name="comment" placeholder="<%= comment %>" required><% if (newThread != null) %><%= newThread.getComment() %></textarea>
			</div>
			
			<div>
				<input type="submit" value="確認">
			</div>
		</form>
			
		<br>
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
		
	</body>
</html>