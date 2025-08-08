<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
  .blind {
    display: none;
  }
</style>
<title>Insert title here</title>
</head>
<body>

  <h1>BBS 계층게시판</h1>
  
  <form method="post"
        action="${contextPath}/bbs/writeBbs">
    <textarea name="content"></textarea>
    <br>
    <button type="submit">게시글 등록</button>
  </form>
  
  <c:if test="${not empty errorBbs}">
    <div style="font-size: 12px; color: red;">${errorBbs}</div>
  </c:if>
  
  <hr>
  
  <c:if test="${not empty errorReply}">
    <div style="font-size: 12px; color: red;">${errorReply}</div>
  </c:if>
  
  <table border="1">
    <tbody>    
      <c:forEach items="${bbsList}" var="bbs">
        <tr>
          <td>
            <!-- depth 들여쓰기 -->
            <c:forEach begin="1" end="${bbs.depth}" step="1">&nbsp;&nbsp;</c:forEach>
            <!-- 답글에 re) 표시 -->
            <c:if test="${bbs.depth ne 0}">re)</c:if>
            <!-- 내용 표시 -->
            ${bbs.content}
          </td>
          <td>
            <c:if test="${bbs.state eq 1}">              
              <!-- 답글달기 버튼 -->
              <button type="button" class="replyFormBtn">답글달기</button>
              <!-- 삭제 버튼 -->
              <button type="button" class="deleteBtn" data-bbs-id="${bbs.bbsId}">삭제하기</button>
            </c:if>
          </td>
        </tr>
        <tr class="blind">
          <td colspan="2">
            <!-- 답글 작성 폼 -->
            <form method="post"
                  action="${contextPath}/bbs/writeReply">
              <!-- 답글 내용 -->
              <textarea name="content" placeholder="답글을 작성하세요."></textarea>
              <!-- 원글 depth / groupId / groupOrder -->
              <input type="hidden" name="depth" value="${bbs.depth}">
              <input type="hidden" name="groupId" value="${bbs.groupId}">
              <input type="hidden" name="groupOrder" value="${bbs.groupOrder}">
              <button type="submit">작성완료</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="2">
          <div class="pagination">
            
            <c:choose>
              <c:when test="${pageDTO.beginPage eq 1}">
                <button type="button" class="disabled-button">&lt;</button>
              </c:when>
              <c:otherwise>
                <button type="button" onclick="location.href='${contextPath}/bbs/list?page=${pageDTO.beginPage-1}'">&lt;</button>
              </c:otherwise>
            </c:choose>
            
            <c:forEach var="p" begin="${pageDTO.beginPage}" end="${pageDTO.endPage}" step="1">
              <c:choose>
                <c:when test="${p eq pageDTO.page}">
                  <button type="button" class="focus-page">${p}</button>
                </c:when>
                <c:otherwise>
                  <button type="button" onclick="location.href='${contextPath}/bbs/list?page=${p}'">${p}</button>
                </c:otherwise>
              </c:choose>
            </c:forEach>
            
            <c:choose>
              <c:when test="${pageDTO.endPage eq pageDTO.pageCount}">
                <button type="button" class="disabled-button">&gt;</button>
              </c:when>
              <c:otherwise>
                <button type="button" onclick="location.href='${contextPath}/bbs/list?page=${pageDTO.endPage+1}'">&gt;</button>
              </c:otherwise>
            </c:choose>
            
          </div>
        </td>
      <tr>
    </tfoot>
  </table>

  <script type="text/javascript">

    const msg = "${msg}";
    if (msg !== "")
      alert(msg);
    
    const deleteBtns = document.getElementsByClassName("deleteBtn");
    for (const deleteBtn of deleteBtns) {      
      deleteBtn.addEventListener("click", function(e) {
        if (confirm("삭제할까요?")) {
          location.href = "${contextPath}/bbs/remove?bbsId=" + e.target.dataset.bbsId;
        }
      })
    }
    
    document.addEventListener("DOMContentLoaded", function(e) {
    
      const replyFormBtns = document.getElementsByClassName("replyFormBtn");  // getElementsByClassName()는 forEach() 불가하므로 for문으로 처리해야 합니다.
      
      for(const replyFormBtn of replyFormBtns) {
        replyFormBtn.addEventListener("click", function(e){
          //----- 현재 클릭한 버튼이 속한 <tr>:①
          const currentTr = replyFormBtn.closest("tr");
          if (!currentTr) {            
            return;
          }
          //----- 클릭한 버튼의 다음 <tr>:② (답글 입력 폼이 있는 <tr>)
          const replyFormTr = currentTr.nextElementSibling;
          if ( !replyFormTr || !replyFormTr.classList.contains("blind") ) {
            return;
          }
          //----- 열고 싶은 replyFormTr을 제외한 다른 모든 replyFormTr 닫기
          const allReplyFormTr = document.querySelectorAll("tr.blind");  // querySelectorAll()는 forEach() 사용이 가능합니다.
          allReplyFormTr.forEach(function(tr) {
            if (tr !== replyFormTr) {
              tr.style.display = "none";
            }
          })
          //----- "답글달기" 버튼을 누를때마다 화면표시/화면숨기기 토글하기
          if (replyFormTr.style.display === "table-row") {
            replyFormTr.style.display = "none";              
          } else {              
            replyFormTr.style.display = "table-row";
          }
        })
      }
      
    })

  
  </script>

</body>
</html>