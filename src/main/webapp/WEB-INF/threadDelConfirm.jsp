<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.ThreadDispInfo" %>
<%@ page import="bulletinBoard.CommentInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bulletinBoard.UserInfo" %>
<%
	ThreadDispInfo deleteThread = null;
	if (request.getAttribute("sendThreadInfo") instanceof ThreadDispInfo) {
		deleteThread = (ThreadDispInfo)request.getAttribute("sendThreadInfo");
	}
	ArrayList<CommentInfo> commentList = null;
	if (request.getAttribute("sendCommentList") instanceof ArrayList) {
		commentList = (ArrayList<CommentInfo>)request.getAttribute("sendCommentList");
	}
	
	UserInfo user = (UserInfo)session.getAttribute("User");
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド削除内容確認</title>
	</head>
	<body>
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
	
		<p>
			削除内容を確認してください
		</p>
		
		<table>
			<tr>
				<th colspan="2">スレッド情報</th>
			</tr>
			<tr>
				<th>スレッドタイトル</th>
				<td><%= deleteThread.getTitle() %></td>
			</tr>
			<tr>
				<th>カテゴリー</th>
				<td><%= deleteThread.getCategory() %></td>
			</tr>
			<tr>
				<th>ユーザー名</th>
				<td><%= deleteThread.getCreateUserName() %></td>
			</tr>
			<tr>
				<th>スレッド作成日</th>
				<td><%= deleteThread.getCreateDate() %></td>
			</tr>
			<tr>
				<th>スレッド内容</th>
				<td><%= deleteThread.getComment() %></td>
			</tr>
		</table>
		
		<br>
		
		<%
			if (commentList != null && commentList.size() > 0) {
				for (int i = 0; i < commentList.size(); i++) {
		%>
		
			<table>
				<tr>
					<th colspan="2">コメント情報</th>
				</tr>
				<tr>
					<th>ユーザー名</th>
					<td><%= commentList.get(i).getPostUserName() %></td>
				</tr>
				<tr>
					<th>コメント投稿日</th>
					<td><%= commentList.get(i).getPostDate() %></td>
				</tr>
				<tr>
					<th>コメント内容</th>
					<td><%= commentList.get(i).getComment() %></td>
				</tr>
			</table>
			
			<br>
			
		<%
				}
			}
		%>
		
		
		<form action="ServletThreadDelConfirm" method="post">
			<input type="submit" value="削除">
			<input type="hidden" name="delete" value="execution">
			<input type="hidden" name="threadId" value="<%= deleteThread.getThreadId() %>">
		</form>
		
		<br>
		
		<a href="ServletThreadSearchList">スレッド一覧へ戻る</a>
		
		
	</body>
</html>