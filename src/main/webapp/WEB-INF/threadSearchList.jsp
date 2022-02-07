<!-- 
	作成者:高橋 作成日:2022/02/04
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スレッド一覧・検索</title>
		<link rel="stylesheet" type="text.css" href="../cssLogout.css">
	</head>
	<body>
		<header class="flex">
			<a href="ServletThreadSearchList">スレッド一覧</a>
			<a href="#">カテゴリー一覧</a>
			<a href="#">アカウント一覧</a>	
			<a href="ServletLogout">ログアウト</a>		
		</header>
		<hr>
		<form action="#">
			<p>
				<span>タイトル:</span>
				<input type="text" name="searchWord	" placeholder="タイトル入力">
				<input type="radio" neme="match" value="部分一致" checked="checked">部分一致
				<input type="radio" neme="match" value="完全一致">完全一致
			</p>
			<p>
				<span>カテゴリー:</span>
				<select name="searchCategory">
					<option value="no">検索条件に含まない</option>
				</select>
				<input type="submit" value="検索">
			</p>
		</form>
		<hr>
		
		<ul>
			<li>
				<a href="#">1</a>
			</li>
			<br>
			<li>
				<a href="#">2</a>
			</li>
			<br>
			<li>
				<a href="#">3</a>
			</li>
			<br>
			<li>
				<a href="#">4</a>
			</li>
			<br>
			<li>
				<a href="#">5</a>
			</li>
			<br>
			<li>
				<a href="#">6</a>
			</li>
			<br>
			<li>
				<a href="#">7</a>
			</li>
			<br>
			<li>
				<a href="#">8</a>
			</li>
			<br>
			<li>
				<a href="#">9</a>
			</li>
			<br>
			<li>
				<a href="#">10</a>
			</li>
		</ul>
		
		<br>
		
		<a href="#">1</a>
		<a href="#">2</a>
		<a href="#">3</a>
		<a href="#">4</a>
		<a href="#">5</a>
		<a href="#">6</a>
		<a href="#">7</a>
		<a href="#">8</a>
		<a href="#">9</a>
		<a href="#">10</a>
	</body>
</html>