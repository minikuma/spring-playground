### External API

---

#### Introduce   

> Spring Boot 에서 외부 서버 자원을 API 로 연동할 때 사용하는 라이브러리들을 적용해 보는 예제 소스 입니다. (RestTemplate)
---

#### Installation

```shell
> git clone: https://github.com/minikuma/spring-playground.git
> ./gradlew clean build -x test
> java -jar /build/libs/*.jar
```    

---

#### Library   

* ```Spring Boot 2.5.7-SNAPSHOT```
* ```RestTemplate```
* ```httpclient version: 4.5.13```   

---

#### Explain    

카카오 검색 API 를 활용하여 검색어를 입력했을 때 해당 검색어의 응답을 받을 수 있다.   

---