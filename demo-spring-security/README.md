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



---
