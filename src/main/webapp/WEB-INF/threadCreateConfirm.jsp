<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewThreadInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%@ page import="bulletinBoard.CategoryNameDisp" %>
<%@ page import="java.util.ArrayList" %>
<%
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
		<title>スレッド作成内容確認</title>
		<link rel="shortcut icon" href="img/bulletin_board.ico">
		<link rel="stylesheet" type="text/css" href="css/design.css">
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />
		
		<div class="text-center">
			<br>
			<p>入力内容を確認して下さい</p>
			<br>
			
			<div class="inline-block">
				<table>
					<tr>
						<th>タイトル</th>
						<td><%= newThread.getTitle() %></td>
					</tr>
					<tr>
						<th>カテゴリー</th>
						<td>
							<%
								for (CategoryNameDisp category : categoryList) {
									if (category.getCategoryId() == newThread.getCategoryId()) {
							%>
										<%= category.getCategoryName() %>
							<%	
										break;
									}
								}
							%>
		
						</td>
					</tr>
					<tr>
						<th>ユーザー名</th>
						<td><%= user.getUserName() %></td>
					</tr>
					<tr>
						<th>スレッド内容</th>
						<td><%= newThread.getComment() %></td>
					</tr>
				</table>
			</div>
			
			<br>
			<br>
			<form action="ServletThreadCreateConfirm" method="post">
				<input class="add width5" type="submit" value="作成">
				<input type="hidden" name="title" value="<%= newThread.getTitle() %>">
				<input type="hidden" name="categoryId" value="<%= newThread.getCategoryId() %>">
				<input type="hidden" name="comment" value="<%= newThread.getComment() %>">
				<input type="hidden" name="createThread" value="execution">
			</form>
			
			<br>
			<br>
			<a href="ServletThreadCreate">スレッド作成画面へ戻る</a>
			<br>
			<br>
		</div>
	</body>
</html>