<!-- 
	作成者:高橋鮎美 作成日:2022/02/07
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.DeleteThreadInfo" %>
<%@ page import="bulletinBoard.CommentInfo" %>
<%@ page import="java.util.ArrayList" %>
<%
	DeleteThreadInfo deleteThread = null;
	if (request.getAttribute("sendDeleteThreadInfo") instanceof DeleteThreadInfo) {
		deleteThread = (DeleteThreadInfo)request.getAttribute("sendDeleteThreadInfo");
	}
	ArrayList<CommentInfo> commentList = null;
	if (request.getAttribute("sendCommentList") instanceof ArrayList) {
		commentList = (ArrayList<CommentInfo>)request.getAttribute("sendCommentList");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド削除内容確認</title>
	</head>
	<body>
		<header>
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="ServletCategorySearchList">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
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
				<td><%= deleteThread.getMakeUserName() %></td>
			</tr>
			<tr>
				<th>スレッド作成日</th>
				<td><%= deleteThread.getMakeDate() %></td>
			</tr>
			<tr>
				<th>スレッド内容</th>
				<td><%= deleteThread.getComment() %></td>
			</tr>
		</table>
		
		<br>
		
		<%
			if (commentList != null && commentList.size() > 0) {
				for (int i = 0; i > commentList.size(); i++) {
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
		
		
		<form action="#">
			<input type="submit" value="削除">
		</form>
		
		<br>
		
		<a href="ServletThreadCreate">スレッド一覧へ戻る</a>
		
		
	</body>
</html>