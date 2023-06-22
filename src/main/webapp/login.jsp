<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>로그인</h2>
	<form action="loginOK.do" method="post">
		아이디 : <input type="text" name="id"><br>
		암호 : <input type="password" name="pwd"><br>
		<input type="submit" value="로그인">
	</form>
	<font color="red">${msg }</font>
</body>
</html>










