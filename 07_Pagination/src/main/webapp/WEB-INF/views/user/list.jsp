<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
  
  <h1>회원 목록</h1>
  
  <a href="${contextPath}/user/list?sort=DESC">최신순</a>
  |
  <a href="${contextPath}/user/list?sort=ASC">과거순</a>
  |
  <select id="size">
    <option>20</option>
    <option>50</option>
    <option>100</option>
  </select>
  <br><br>
  
  <table border="1">
    <tbody>
      <c:forEach items="${users}" var="user">
        <tr>
          <td>순번</td>
          <td>${user.lastName}</td>
          <td>${user.firstName}</td>
          <td>${user.email}</td>
          <td>${user.gender}</td>
          <td>${user.ipAddress}</td>
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">${pagingHtml}</td>
      </tr>
    </tfoot>
  </table>
  
  <script type="text/javascript">
  	const selectSize = document.getElementById("size");
  	selectSize.addEventListener("change", function(e){
  	  location.href = "${contextPath}/user/list?size=" + selectSize.value;
  	})
  	
  	selectSize.value = ${size};
  </script>
  
</body>
</html>