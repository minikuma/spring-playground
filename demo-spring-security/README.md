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

(5) 위임 필터 및 필터 빈 초기화
* 서블릿 스펙을 구현하고 있는 서블릿은 스프링에서 정의한 빈을 주입할 수 없음
* ```DelegatingFilterProxy``` 를 통해 사용 가능
  * ```FilterChainProxy```
    * ```springSecurityFilterChain``` 이름으로 생성되는 필터 빈
    * 스프링 시큐리티가 초기화 될때 생성되는 필터를 관리하고 제어(커스텀 필터도 등록 가능)
    * 필터 순서대로 탐색

(6) 필터 초기화와 다중 설정 클래스
* ```WebSecurityConfigurerAdapter```
* 설정 클래스 별로 RequestMatcher 설정 가능
* 다중 설정 클래스 사용 가능, 설정 클래스 별로 필터가 생성
---
