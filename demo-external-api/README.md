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

#### API

```HTTP 1.1 GET http://localhost:8080/search?query={검색어}```

---
#### 추가 기능
* Retry, Recover 기능 추가
* Key work 클라이언트를 통해 입력받는 구조로 변경
* 키워드에 대한 결과는 최대 10건 까지 응답

<`{자전거}` 키워드 입력 예시>
```json
{
    "places": [
        {
            "placeName": "광명스피돔 경륜본장"
        },
        {
            "placeName": "여의나루역 1번출구 앞 대여소"
        },
        {
            "placeName": "제주도자전거대여 보물섬하이킹"
        },
        {
            "placeName": "수월봉전기자전거"
        },
        {
            "placeName": "위너스 스포츠클럽 골프연습장옆 대여소 금천구점"
        },
        {
            "placeName": "라이트브라더스"
        },
        {
            "placeName": "하이텐"
        },
        {
            "placeName": "아라뱃길자전거대여소"
        },
        {
            "placeName": "스페셜라이즈드 코리아"
        },
        {
            "placeName": "트라이엄프 코리아 플래그십스토어"
        }
    ]
}
```