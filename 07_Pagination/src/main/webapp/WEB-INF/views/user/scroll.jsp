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
<style>
  .user {
    box-sizing: border-box;
    width: 1024px;
    height: 120px;
    margin: 10px auto;
    border: 1px solid gray;
    cursor: pointer;
    text-align: center;
    line-height: 120px;
  }
  .user > span {
    color: tomato;
    display: inline-block;
    box-sizing: border-box;
  }
  .user > span:nth-of-type(n) { width: 19.5%; }
</style>
</head>
<body>
  
  <h1 style="text-align: center;">회원 목록</h1>
  
  <div style="text-align: center; margin-bottom: 20px;">
    <button id="sort-desc" type="button" style="margin-right: 10px;">최신순</button>
    <button id="sort-asc" type="button">과거순</button>
  </div>
  
  <div id="user-list"></div>
  <div id="scroll-anchor"></div> <!-- 감지 대상 요소 -->
  
  <div id="loading" style="display:none;">로딩중...</div>
  <div id="end" style="display:none;">모든 게시물을 다 불러왔습니다.</div>
  
  <script>
  
    let page = 1;
    let pageCount = 0;
    let isLoading = false;
    let isEnd = false;
    let sort = "DESC";  // 디폴트로 DESC 설정
    
    //----- 회원 리스트 렌더링
    function renderUserList(users) {
      const userList = document.getElementById("user-list");
      users.forEach(user => {
        const div = document.createElement("div");
        div.className = "user";
        div.dataset.uid = user.uid;
        div.innerHTML = `
          <span>\${user.lastName}</span>
          <span>\${user.firstName}</span>
          <span>\${user.email}</span>
          <span>\${user.gender}</span>
          <span>\${user.ipAddress}</span>
        `;
        userList.appendChild(div);
      });
    }
    
    //----- 로딩/종료 메시지 표시 제어
    function showLoading(show) {
      document.getElementById("loading").style.display = show ? "block" : "none";
    }
    function showEndMessage(show) {
      document.getElementById("end").style.display = show ? "block" : "none";
    }
    
    //----- 데이터 요청
    async function fetchUserList() {
      //***** 로딩중 또는 마지막 페이지 도달 시 요청 중지
      if (isLoading || isEnd) 
        return;
      //***** 데이터 가져오기 전에는 로딩중으로 표시
      isLoading = true;
      showLoading(true);
      //***** 데이터 가져오기
      try {
        const response = await fetch(`${contextPath}/api/user/scroll/list?page=\${page}&sort=\${sort}`);
        if (!response.ok)
          throw new Error(response.status);
        const jsonData = await response.json();
        pageCount = jsonData.pageCount || 0;
        const users = jsonData.users;
        if (users && users.length > 0) {  // 회원 리스트를 가져왔다면 화면에 렌더링
          renderUserList(users);
        }
        if (page >= pageCount) {  // 마지막 페이지 도달 처리
          isEnd = true;
          showEndMessage(true);
          observer.disconnect(); // 더 이상 관찰 중단
        }
      } catch (error) {
        alert("목록 요청 실패: " + error.message);
      } finally {
        isLoading = false;
        showLoading(false);
      }
    }
    
    //----- 정렬 변경 함수
    function changeSort(newSort) {
      // 이미 선택된 정렬이면 무시함
      if (sort === newSort)
        return;
      // 정렬 변경
      sort = newSort;
      // 상태 초기화
      page = 1;
      pageCount = 0;
      isLoading = false;
      isEnd = false;
      document.getElementById("user-list").innerHTML = "";
      showEndMessage(false);
      showLoading(false);
      // 목록 가져오기
      fetchUserList();
    }
    
    //----- 정렬 버튼 이벤트
    document.getElementById("sort-desc").addEventListener("click", function(e){
      changeSort("DESC");
    });
    document.getElementById("sort-asc").addEventListener("click", function(e){
      changeSort("ASC");
    });
    
    /*
      Intersection Observer API
      
      1. 특정 요소가 사용자 화면(뷰포트)에 들어오거나 나가는 걸 비동기적으로 감지할 수 있게 도와주는 웹 표준 API입니다.
      2. 무한 스크롤, Lazy Loading 이미지 등에 유용하게 사용할 수 있습니다.
      3. 직접 스크롤 이벤트를 사용하는 것보다 효율적이며 성능이 더 좋습니다.
    */

	//----- Intersection Observer 콜백함수
    const callback = (entries, observer) => {
      entries.forEach(entry => {
        if (entry.isIntersecting && !isLoading && !isEnd) {
          page++;
          fetchUserList();
        }
      });
    };
    
    //----- Intersection Observer 생성
    //   1. new IntersectionObserver(callback, options) 형태로 생성합니다.
    //   2. callback 함수는 관찰 대상 요소가 뷰포트와 교차할 때(화면에 보일 때) 호출됩니다.
    //      1) 두 개 인자를 받습니다. (entries, observer)
    //      2) entries : 관찰 중인 요소에 대한 배열로 각 항목(Entry)은 관찰 대상의 상태를 저장합니다.
    //         (1) isIntersecting    : 현재 요소가 뷰포트와 교차 중이면 true, 아니면 false
    //         (2) intersectionRatio : 요소가 보이는 비율 (0 ~ 1)
    //         (3) target            : 관찰 중인 대상 요소
    //   3. options는 관찰 기준을 설정하는 객체입니다.
    //      1) root       : 관찰 기준 요소 (null은 브라우저의 뷰포트를 의미합니다. 디폴트가 null입니다.)
    //      2) rootMargin : 뷰포트 주변 마진을 지정 (해당 영역을 포함해서 관찰합니다.)
    //      3) threshold  : 관찰 대상 요소가 얼마나 보이면 callback을 호출할 것인지 지정 (0은 조금이라도 보이면 호출, 1은 100% 보여야 호출)
    //   4. 관찰 시작 및 중지
    //      1) observer.observe(요소) : 요소 관찰 시작
    //      2) observer.disconnect() : 요소 관찰 중지 (observer.unobserver(요소)도 가능)
    const observer = new IntersectionObserver(callback, {
      root: null, //---------- 뷰포트 기준
      rootMargin: "50px", //-- 뷰포트 바깥 50px 내에서 미리 로딩 가능
      threshold: 0  //-------- 조금이라도 보이면 callback 실행
    });
    
    // 초기화 함수
    function initInfiniteScroll() {
      page = 1;
      pageCount = 0;
      isLoading = false;
      isEnd = false;
    
      document.getElementById("user-list").innerHTML = "";
      showEndMessage(false);
      showLoading(false);
    
      fetchUserList();
    
      // 감지 대상 요소를 옵저버에 등록
      const anchor = document.getElementById("scroll-anchor");
      observer.observe(anchor);
    }
    
    // DOM 준비 시 초기화
    window.addEventListener("DOMContentLoaded", initInfiniteScroll);
    
  </script>

</body>
</html>