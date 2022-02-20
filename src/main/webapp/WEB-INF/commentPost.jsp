<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.NewCommentInfo" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	String message = (String)request.getAttribute("message");
	NewCommentInfo newComment = null;
	if (session.getAttribute("NewCommentInfo") instanceof NewCommentInfo) {
		newComment = (NewCommentInfo)session.getAttribute("NewCommentInfo");
	}
	UserInfo user = (UserInfo)session.getAttribute("User");
	int threadId = (Integer)request.getAttribute("sendThreadId");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>コメント入力</title>
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
			<form action="ServletCommentPostConfirm" method="post">
				
				<div class="input-margin">
					<div class="inline-block input-item">
						ユーザー名:				
					</div>
					<div class="input-width inline-block">
						<%= user.getUserName() %>
					</div>
				</div>
				
				<div class="input-margin">
					<div class="inline-block input-item top">
						<label for="comment">
							<span class="caution-color">＊</span>コメント内容:
						</label>				
					</div>
					<span>
						<textarea class="input-width" rows="10" id="comment" name="comment" placeholder="コメント内容" required><% if (newComment != null) %><%= newComment.getComment() %></textarea>
					</span>
				</div>
				
				<div  class="input-margin">
					<input type="submit" value="確認">
				</div>
				<input type="hidden" name="threadId" value="<%= threadId %>">
			</form>
		</div>

		<br>	
		<br>
		<div class="text-center">
			<a href="ServletThreadDetail?threadId=<%= threadId %>">スレッド詳細画面へ戻る</a>
		</div>
		<br>	
		<br>
	</body>
</html>