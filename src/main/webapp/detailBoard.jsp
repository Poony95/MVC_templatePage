<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시물 상세</h2>
	<hr>
	글번호 : ${b.no }<br>
	글제목 : ${b.title }<br>
	작성자 : ${b.writer }<br>
	글내용 : <br>
	<textarea readonly="readonly" rows="10" cols="80">${b.content }</textarea><br>
	등록일 : ${b.regdate }<br>
	조회수 : ${b.hit }<br>

	
	<c:if test="${not empty b.fname }">
		<img src="board/${b.fname }" width="200" height="200">
	</c:if>

	
	<hr>
	
	<a href="insertBoard.do?no=${b.no }">답글작성</a>
	
</body>
</html>





















