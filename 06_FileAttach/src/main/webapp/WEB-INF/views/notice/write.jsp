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
<h1>공지사항 작성하기</h1>
    <form method="post"
          action="${contextPath}/notice/write"
          enctype="multipart/form-data"
          id="writeForm">
    
    <label>제목 : <input type="text" name="title"/></label>
    <br>
    <textarea name="content"></textarea>
    <br>
    <input type="file" name="files" id="files" multiple/>
    <br>
    <button type="submit">작성하기</button>
    </form>

  <script type="text/javascript">
       const files = document.getElementById("files");
       const limitPerFile = 1024 * 1024 * 10;  // 10MB
       const limitTotal = 1024 * 1024 * 100;	// 100MB
       files.addEventListener("change", function(e) { 
         let total = 0;
         for(const file of files.files) { 
         if (file.size > limitPerFile) {
             alert('첨부 파일 최대 크기는 10MB 입니다.');
             file.value = ""; // 첨부된 파일 초기화
             return;
           }
           total += file.size;
           if (total > limitTotal) {
             alert('전체 첨부 파일 최대 크기는 100MB입니다.');
             files.value = "";	//----- 첨부된 파일 초기화
             return;
           } 
         }
       });
    
    </script>
    
    
</body>
</html>