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

  <!-- 정규식 / 이메일 인증 / 비밀번호 확인 포함 필요 -->

  <h1>회원 가입 화면</h1>
  
  <form method="post"
        action="${contextPath}/user/signup">
  
    <label>이메일 : <input type="text" name="email" id="email"></label>
    <span style="font-size: 12px; color: red;" id="email-msg"></span>
    <br>
    
    <label>비밀번호 : <input type="password" name="password"></label>
    <br>
    
    <label>닉네임 : <input type="text" name="nickname"></label>
    <span style="font-size: 12px; color: red;" id="nickname-msg"></span>
    <br>
    
    <button type="submit">회원가입</button>
    
  </form>
  
  <c:if test="${not empty error}">
    <div style="font-size: 12px; color: red;">${error}</div>
  </c:if>
  
  <script type="text/javascript">
  	//----- 이메일 입력 완료 후 이메일 중복을 체크합니다.
  	function emailDuplicateCheck() {
  	  const email = document.getElementById("email");
  	  email.addEventListener("blur", function(e) {
  	    fetch("${contextPath}/user/emailDuplicateCheck?email=" + email.value)
  	      .then(response => response.json())
  	      .then(jsonData => {
  	        // console.log(jsonData);
  	        if ( !(jsonData.result) ) {
  	          document.getElementById("email-msg").textContent = jsonData.msg;
  	        } else {
  	          document.getElementById("email-msg").textContent = "";
  	        }
  	      })
  	  })
  	}
  	emailDuplicateCheck();
  </script>
  
</body>
</html>