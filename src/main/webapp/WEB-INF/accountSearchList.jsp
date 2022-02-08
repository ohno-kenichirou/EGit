<!-- 
	内容:	アカウント一覧画面
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String message = (String)request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント一覧</title>
</head>
<body>
	<header class="flex">
		<a href="ServletThreadSearchList">スレッド一覧</a>
		<a href="#">カテゴリー一覧</a>
		<a href="#">アカウント一覧</a>	
		<a href="ServletLogout">ログアウト</a>		
	</header>
	<hr>
	<form action="ServletAccountSearchList" method="post">
		<p>
			<span>ユーザー名:</span>
			<input type="text" name="userName" placeholder="ユーザー名入力">
			<input type="radio" name="selectMatch" value="partial" checked="checked">部分一致
			<input type="radio" name="selectMatch" value="perfect">完全一致
			<input type="submit" value="検索">
		</p>
	</form>
	<hr>
	
	<%
		if (message != null && message != "") {
	%>
			<div>
				<%= message %>
			</div>
	<%	
		}
	%>
	
	<form action="ServletAccountRegister" method="post">
		<input type="submit" value="アカウント登録">
	</form>
	
	<table>
		<tr>
			<th></th>
			<th></th>
			<th>会員ID</th>
			<th>メールアドレス</th>
			<th>ユーザー名</th>
			<th>生年月日</th>
			<th>性別</th>
			<th>管理者権限の有無</th>
			<th>ロック</th>
			<th>登録者</th>
			<th>登録日</th>
			<th>更新者</th>
			<th>更新日</th>
		</tr>
		
				
		<tr>
			<td>
				<form action="ServletAccountModify" method="post">
					<input type="hidden" name="userId">
					<input type="submit" value="修正">
				</form>
			</td>
			<td>
				<form action="ServletAccountDelConfirm" method="post">
					<input type="hidden" name="userId">
					<input type="submit" value="削除">
				</form>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>

		
	</table>
	
	<a href="ServletAccountSearchList?page=">1</a>

</body>
</html>