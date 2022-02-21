<!-- 
	処理内容：カテゴリー削除確認画面

	作成者:大野賢一朗 作成日:2022/02/07
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.CategoryInfo" %>
<%
	String message = (String)request.getAttribute("message");
	CategoryInfo category = (CategoryInfo)session.getAttribute("CategoryDel");
	String name = "";
	String kana = "";
	if (category != null) {
		name = category.getCategoryName();
		kana = category.getCategoryKana();
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>カテゴリー削除確認</title>
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
			<div class="col-md-12">
				<div class="text-center">
					
					<p>削除内容を確認して下さい</p>
					<div class="inline-block">
					<table>
						<tr>
							<th>カテゴリー名</th>
							<td><%= name %></td>
						</tr>
						<tr>
							<th>カテゴリー名(カナ)</th>
							<td><%= kana %></td>
						</tr>
					</table>
					</div>
					<br>
					
					<%
						if (message != null && !message.equals("")) {
					%>
							<div class="caution-text"><%= message %></div>
					<%	
						}
					%>
					
					<form action="ServletCategoryDelConfirm" method="post">
						<input type="submit" class="delete width5" value="削除">
					</form>
					<div>
						<a href="ServletCategorySearchList">カテゴリー一覧画面へ戻る</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>