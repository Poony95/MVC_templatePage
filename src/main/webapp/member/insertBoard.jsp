<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>${headerLine }</h2>
<hr>
<form action="insertBoardOK.do" method="post"
			enctype="multipart/form-data">
	
	<input type="hidden" name="no" value="${no }">
	<!-- 등록하려는 게시물의 글번호가 아니고
			부모글의 글번호를 상태유지하여 보냅니다.
			만약, 새글이면 0,
			답글이면 0이 아닌값(부모글의 글번호) -->
	
	글제목 : <input type="text" name="title" value="${title }"><br>
	작성자 : <input type="text" name="writer" value="${id }"><br>
	글암호 : <input type="password" name="pwd"><br>
	글내용 : <br>
	<textarea rows="10" cols="60" name="content"></textarea><br>
	첨부파일 : <input type="file" name="fname"><br>
	<input type="submit" value="등록">
	<input type="reset" value="다시입력">
</form>
</body>
</html>











