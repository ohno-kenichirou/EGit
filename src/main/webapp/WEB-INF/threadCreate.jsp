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
		<link rel="stylesheet" type="text/css" href="css/design.css">
	</head>
	<body>
		<jsp:include page="header.jsp" flush="true" />
		
		<%
			if (message != null && message.equals("未入力の項目があります")) {
		%>
				<br>
				<div class="caution-text">
					<%= message %>
				</div>
		<%	
			}
		%>
		<br>
		<div class="text-center font12">
			<span class="caution-color">＊</span>マークは入力必須項目
		</div>
		<br>
		
		<div class="text-center">
			<form action="ServletThreadCreateConfirm" method="post">
				<div class="input-margin">
					<div class="inline-block  a">
						<label for="title">
							<span class="caution-color">＊</span>タイトル:
						</label>				
					</div>
					<span>
						<input class="input-width" type="text" id="title" name="title" placeholder="<%= title %>" required <%
																											if (newThread != null) {
																										%>
																												value="<%= newThread.getTitle() %>"																								
																										<%
																											}
																										%>>
					</span>
				</div>
				
				<div class="input-margin">
					<div class="inline-block  a">
						<label for="category">
							<span class="caution-color">＊</span>カテゴリー:
						</label>				
					</div>
								
					<span>
						<select class="input-width" id="category" name="categoryId">
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
					</span>
				</div>
				
				<div class="input-margin">
					<div class="inline-block  a top">
						<label for="comment">
							<span class="caution-color">＊</span>スレッド内容:
						</label>				
					</div>
					<span>
						<textarea class="input-width" rows="10" id="comment" name="comment" placeholder="<%= comment %>" required><% if (newThread != null) %><%= newThread.getComment() %></textarea>
					</span>
				</div>
				
				<div class="input-margin">
					<input type="submit" value="確認">
				</div>
			</form>
		</div>
		
		
		<br>	
		<br>
		<div class="text-center">
			<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
		</div>
		<br>	
		<br>
	</body>
</html>