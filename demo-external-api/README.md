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
      "placeName": "광명스피돔 경륜본장",
      "addressName": "경기 광명시 광명동 780",
      "placeUrl": "http://place.map.kakao.com/9684313"
    },
    {
      "placeName": "여의나루역 1번출구 앞 대여소",
      "addressName": "서울 영등포구 여의도동 2-1",
      "placeUrl": "http://place.map.kakao.com/1346674874"
    },
    {
      "placeName": "제주도자전거대여 보물섬하이킹",
      "addressName": "제주특별자치도 제주시 용담이동 2706-1",
      "placeUrl": "http://place.map.kakao.com/815138345"
    },
    {
      "placeName": "수월봉전기자전거",
      "addressName": "제주특별자치도 제주시 한경면 고산리 3674-10",
      "placeUrl": "http://place.map.kakao.com/1375519951"
    },
    {
      "placeName": "위너스 스포츠클럽 골프연습장옆 대여소 금천구점",
      "addressName": "서울 금천구 독산동 1066-2",
      "placeUrl": "http://place.map.kakao.com/21308121"
    },
    {
      "placeName": "라이트브라더스",
      "addressName": "서울 용산구 서빙고동 180",
      "placeUrl": "http://place.map.kakao.com/1793686905"
    },
    {
      "placeName": "하이텐",
      "addressName": "경기 용인시 기흥구 보정동 619-2",
      "placeUrl": "http://place.map.kakao.com/11628752"
    },
    {
      "placeName": "아라뱃길자전거대여소",
      "addressName": "인천 계양구 귤현동 451-556",
      "placeUrl": "http://place.map.kakao.com/232574592"
    },
    {
      "placeName": "스페셜라이즈드 코리아",
      "addressName": "서울 용산구 한남동 714",
      "placeUrl": "http://place.map.kakao.com/1004867759"
    },
    {
      "placeName": "트라이엄프 코리아 플래그십스토어",
      "addressName": "서울 강동구 둔촌동 609-7",
      "placeUrl": "http://place.map.kakao.com/1216134057"
    }
  ]
}
```