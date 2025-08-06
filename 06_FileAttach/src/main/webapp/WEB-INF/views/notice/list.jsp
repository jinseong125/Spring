<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>공지사항 목록</title>
  <!-- Font Awesome CDN 추가 -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
</head>
<body>
  <a href="${contextPath}/notice/write">작성</a>
  
  <hr>
  <h1>공지사항 목록</h1>
  <table border="1">
    <tbody>
      <c:forEach items="${notices}" var="notice">
        <tr>
          <td>
            <a href="${contextPath}/notice/detail?nid=${notice.nid}">
              ${notice.title}
            </a>
          </td>
          <td>
            <c:if test="${notice.attachCount ne 0}">
              <i class="fa-solid fa-paperclip"></i> ${notice.attachCount}
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>
