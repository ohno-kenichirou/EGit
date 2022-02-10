<!-- 
	処理内容:	アカウント修正画面
			
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
<title>アカウント修正</title>
</head>
<body>
		<header class="flex">
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="#">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		
		<%
			if (message != null && !message.equals("")) {
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
	<form action="ServletAccountRegister" method="post">
		<div>
			<label for="userId">
				<span>＊</span>会員ID:
			</label>				
		</div>
		<div>
			<input type="text" id="userId" name="userId"  disabled="disabled">
		</div>
		<div>
			<label for="email">
				<span>＊</span>メールアドレス:
			</label>				
		</div>
		<div>
			<input type="text" id="email" name="email" placeholder="メールアドレス入力" required>
		</div>
		<div>
			<label for="pass">
				<span>＊</span>パスワード:
			</label>				
		</div>
		<div>
			<input type="password" id="pass" name="pass" placeholder="パスワード入力" required>
		</div>
		<div>
			<label for="userName">
				<span>＊</span>ユーザー名:
			</label>				
		</div>
		<div>
			<input type="text" id=userName name="userName" placeholder="ユーザー名入力" required>
		</div>
		<div>
			<label for="birth">
				<span>＊</span>生年月日:
			</label>				
		</div>
		<div>
			<input type="date" id=birth name="birth" placeholder="生年月日入力" required>
		</div>
		<div>
			<label>
				<span>＊</span>性別:
			</label>				
		</div>
		<div>
			<input type="radio" name="gender" value="1">女性
			<input type="radio" name="gender" value="2">男性
			<input type="radio" name="gender" value="3">その他
		</div>
		<div>
			<label>
				管理者権限の有無：
			</label>				
		</div>
		<div>
			<input type="radio" name="manager" value="1">有：管理ユーザー
			<input type="radio" name="manager" value="0" checked="checked">無：会員ユーザー
		</div>
		<div>
			<label>
				ロック解除の有無：
			</label>				
		</div>
		<div>
			<input type="radio" name="lift" value="1">有：解除する
			<input type="radio" name="lift" value="0" checked="checked">無
		</div>
		<div>
			<input type="submit" value="確認">
		</div>
	</form>
	<div>
		<a href="ServletAccountSearchList">アカウント一覧へ戻る</a>
	</div>
</body>
</html>