<!-- 
	処理内容:	カテゴリー修正画面
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bulletinBoard.CategoryInfo" %>
<%
	String message = (String)request.getAttribute("message");
	CategoryInfo category = (CategoryInfo)session.getAttribute("CategoryModify");
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
	<title>カテゴリー修正</title>
	<link rel="shortcut icon" href="img/bulletin_board.ico">
	<link rel="stylesheet" type="text/css" href="css/design.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/design2.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/main.min.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/style.css" media="all">
</head>
<body>
	<div id="wrap">
		<div class="container mb-45">
			<div class="row">
				<div class="col-md-12">
					<jsp:include page="header.jsp" flush="true" />
					
					<%
						if (message != null && !message.equals("")) {
					%>
							<div class="caution-text">
								<%= message %>
							</div>
					<%	
						}
					%>
					
					<div class="text-center">
						<span class="caution-color">＊</span>マークは入力必須項目
					</div>
					<form action="ServletCategoryModify" method="post">
						<div>
							<label for="categoryName">
								<span class="caution-color">＊</span>カテゴリー名:
							</label>				
						</div>
						<div>
							<input type="text" id="categoryName" name="categoryName" placeholder="カテゴリー名入力" maxlength="50" size="50" required value="<%= name %>">
						</div>
						<div>
							<label for="categoryKana">
								<span class="caution-color">＊</span>カテゴリー名(カナ):
							</label>				
						</div>
						<div>
							<input type="text" id="categoryKana" name="categoryKana" placeholder="カテゴリー名(カナ)入力" maxlength="100" size="100" required value="<%= kana %>">
						</div>
						<input type="hidden" name="fromPage" value="categoryModify">
						<div>
							<input type="submit" value="確認">
						</div>
					</form>
					<div>
						<a href="ServletCategorySearchList">カテゴリー一覧へ戻る</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>