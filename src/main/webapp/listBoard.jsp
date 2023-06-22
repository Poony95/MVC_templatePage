<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#btnAll").click(function(){
			alert("OK");
			$("#all").val("yes");
			$("#f").submit();
		});
	})
</script>


</head>
<body>
	<h2>게시물 목록</h2>
	<hr>
	<a href="member/insertBoard.do">게시물 등록</a>
	<a href="listGoods.do">상품관리</a>
	<br>
	<form  id="f"  action="listBoard.do" method="post">
		<input type="hidden" name="all" id="all" value="no">
		<select name="searchColumn" id="searchColumn">
			<option value="title">글제목</option>
			<option value="content">글내용</option>
			<option value="writer">작성자</option>
		</select>
		
		 : <input type="search" name="keyword"> 
		<input type="submit" value="검색">
		<input type="button" value="모두검색" id="btnAll">
	</form>
	
	<table border="1" width="80%">
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>작성자</th>
		</tr>
		<c:forEach var="b" items="${list }">
			<tr>
				<td>${b.no }</td>
				<td>
					<c:if test="${b.b_level != 0 }">					
						<c:forEach var="i" begin="1" end="${b.b_level }">
							&nbsp;&nbsp;
						</c:forEach>
						<img  src="re.png">						
					</c:if>
					<a href="detailBoard.do?no=${b.no }">${b.title }</a>					
				</td>
				<td>${b.writer }</td>
			</tr>
		</c:forEach>
		
	</table>
	<hr>
	
	<c:forEach var="i" begin="1" end="${totalPage }">
		<a href="listBoard.do?pageNUM=${i }">${i }</a>&nbsp;
	</c:forEach>
	
	
</body>
</html>













