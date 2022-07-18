## Spring Security

---

### Features

(1) Security API & Filter
* Remember Me Filter
  * ```RememberMeAuthenticationFilter```
    * Remember Me Cookie 를 발급받은 경우
    * 인증(Authentication) 객체가 없는 경우
* AnonymousAuthenticationFilter
  * 익명 사용자 인증 처리 필터
  * 익명 사용자와 인증 사용자를 구분해서 처리하기 위한 용도
  * 화면에서 ```isAnonymous()```, ```isAuthenticated()```
  * 인증 객체를 세션에 저장하지 않는다.
* 동시 세션 제어
  * 이전 사용자 세션 만료
  * 현재 사용자 인증 실패
  * ```http.sessionManagement()```
* 세션 고정 보호
* 세션 정책
  * always
  * if_required
  * never
  * stateless (JWT 와 같은)

(2) 인가 API - 권한 설정
* 선언적 방식
  * URL: ```http.antMatcher("/users/**").hasRole("USER")```
  * Method: ```@PreAuthorize("hasRole('USER')"```
* 동적 방식 (DB 연동)
  * URL
  * Method

(3) 인가 API - ExceptionTranslationFilter
  * AuthenticationException
    * 인증 예외 처리
      * AuthenticationEntryPoint 호출: 로그인 페이지 이동, 401 오류 전달 등
    * 인증 예외가 발생하기 전의 요청 정보를 저장
      * RequestCache
      * SavedRequest
  * AccessDeniedException
    * 인가 예외 처리
      * AccessDeniedHandler 에서 예외 처리가능
  * 사용법: ```http.exceptionHandlering()```

(4) Form 인증 (CSRF)
  * ```http.csrf()```
---
