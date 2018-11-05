<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../../../temp/bootStrap.jsp"></c:import>
<script type="text/javascript">
	$(function() {
		$("#btn").click(function() {
			var writer = $("#writer").val();
			var contents = $("#contents").val();
			$.post("./memoWrite.do", {
				writer : writer,
				contents : contents
			}, function(data) {
				alert(data);
				location.reload();
			});
		})

		$("#del").click(function() {
			$(".del").each(function() {
				if ($(this).prop("checked")) {
					var num = $(this).attr("id");
					$.get("./memoDelete.do?num=" + num, function() {
						location.reload();
					})
				}
			})
		})
		$(".del").click(function() {
			$(".del").each(function() {
				if ($("#check").prop("checked")) {
					$(this).prop("checked") = $("#check").prop;
				} else if (!$(this).prop("checked")) {
					$("#check").prop = $(this).prop("checked");
				}
			})
		})
	})
</script>
</head>
<body>
	<c:import url="../../../temp/header.jsp"></c:import>

	<div class="container-fluid">
		<div class="row">
			<table class="table table-hover">
				<tr>
					<td width="5%"><input type="checkbox" id="check"></td>
					<td width="5%">NUM</td>
					<td width="10%">WRITER</td>
					<td width="70%">CONTENTS</td>
					<td width="10%">DATE</td>
				</tr>
				<c:forEach items="${list}" var="memoDTO">
					<tr>
						<td><input type="checkbox" class="del" id="${memoDTO.num}"
							name="del"></td>
						<td>${memoDTO.num}</td>
						<td>${memoDTO.writer}</td>
						<td>${memoDTO.contents}</td>
						<td>${memoDTO.reg_date}</td>
					</tr>
				</c:forEach>
			</table>
			<table>
				<tr>
					<td><input type="button" id="btn" class="btn btn-default"
						value="등록"></td>
					<td></td>
					<td width="10%"><input type="text" class="form-control"
						id="writer" name="writer"></td>
					<td width="70%"><input type="text" class="form-control"
						id="contents" name="contents"></td>
					<td width="10%"></td>
				</tr>
				<tr>
					<td>
						<button class="btn btn-default" id="del">삭제</button>
					</td>
				</tr>
			</table>

			<div class="row" align="center">
				<ul class="pagination">
					<li><a href="./memoList.do?curPage=1"><span
							class="glyphicon glyphicon-backward"></span></a></li>

					<c:if test="${pager.curBlock gt 1}">
						<li><a href="./memoList.do?curPage=${pager.startNum-1}"><span
								class="glyphicon glyphicon-chevron-left"></span></a></li>
					</c:if>
					<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
						<li><a href="./memoList.do?curPage=${i}">${i}</a></li>
					</c:forEach>

					<c:if test="${pager.curBlock lt pager.totalBlock}">
						<li><a href="./memoList.do?curPage=${pager.lastNum+1}"><span
								class="glyphicon glyphicon-chevron-right"></span></a></li>
					</c:if>
					<li><a href="./memoList.do?curPage=${pager.totalPage}"><span
							class="glyphicon glyphicon-forward"></span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<c:import url="../../../temp/footer.jsp"></c:import>
</body>
</html>