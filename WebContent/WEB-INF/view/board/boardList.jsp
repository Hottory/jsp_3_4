<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../../../temp/bootStrap.jsp" />
</head>
<body>
	<c:import url="../../../temp/header.jsp" />
	<div class="container-fluid">
		<div class="row">
			<h1>${board.toUpperCase()}</h1>
		</div>
		<div class="row">
			<div>
				<form class="form-inline" action="./${board}List.do">
					<div class="form-group">
						<select class="form-control" id="sel1" name="kind">
							<option>Title</option>
							<option>Contents</option>
							<option>Writer</option>
						</select> <input type="text" class="form-control" id="search"
							placeholder="Enter search" name="search">
					</div>

					<button type="submit" class="btn btn-default">Submit</button>
				</form>

			</div>

			<table class="table table-hover">
				<tr>
					<td>NUM</td>
					<td>TITLE</td>
					<td>WRITER</td>
					<td>DATE</td>
					<td>HIT</td>
				</tr>
				<c:forEach items="${list}" var="boardDTO">

					<tr>
						<td>${boardDTO.num}</td>
						<td><a href="./${board}SelectOne.do?num=${boardDTO.num}">
								<c:catch>
									<c:forEach begin="1" end="${boardDTO.depth}">
						--
					</c:forEach>
								</c:catch> ${boardDTO.title}
						</a></td>
						<td>${boardDTO.writer}</td>
						<td>${boardDTO.reg_date}</td>
						<td>${boardDTO.hit}</td>
					</tr>
				</c:forEach>
			</table>
			<c:choose>
				<c:when test="${board eq 'notice'}">
					<c:if test="${not empty member and member.kind eq 'T'}">
						<c:import url="../../../temp/writeButton.jsp" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty member}">
						<c:import url="../../../temp/writeButton.jsp" />
					</c:if>
				</c:otherwise>
			</c:choose>
			<div class="row" align="center">

				<ul class="pagination">
					<li><a href="./${board}List.do?curPage=1"><span
							class="glyphicon glyphicon-backward"></span></a></li>

					<c:if test="${pager.curBlock gt 1}">
						<li><a href="./${board}List.do?curPage=${pager.startNum-1}"><span
								class="glyphicon glyphicon-chevron-left"></span></a></li>
					</c:if>

					<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
						<li><a href="./${board}List.do?curPage=${i}">${i}</a></li>
					</c:forEach>


					<c:if test="${pager.curBlock lt pager.totalBlock}">
						<li><a href="./${board}List.do?curPage=${pager.lastNum+1}"><span
								class="glyphicon glyphicon-chevron-right"></span></a></li>
					</c:if>


					<li><a href="./${board}List.do?curPage=${pager.totalPage}"><span
							class="glyphicon glyphicon-forward"></span></a></li>
				</ul>

			</div>
		</div>
	</div>


	<jsp:include page="../../../temp/footer.jsp" />
</body>
</html>