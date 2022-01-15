## Spring Boot Redis    

--- 

Spring 에서 지원하고 있는 여러 Redis Connection 방법과 그에 따른 설정과 테스트를 한 샘플 소스 입니다. Cache 로 Redis 를 활용할 수 있는 예제가 포함되어 있습니다.    

* LettuceConnection
  * Stand Alone (```In-Process```)
  * Master-Replica (```Not-Ready```)
* JedisConnection
  * Sentinel
* RedisTemplate 을 통한 Object 작업  
* Redis Repositories 을 통한 Object 작업 (```In-Process```)

---
### Installation   
* Docker 를 통한 Redis 설치   
```docker pull redis```   
```docker run --name redis -p 6380:6379 redis --requirepass your password```
* Redis Cli 접근   
```docker run -it --link some-redis:redis --rm redis redis-cli -h redis -p 6379```    
* Reference: https://hub.docker.com/_/redis

---

### Using Redis   

* Redis Repositories    
* RedisTemplate

---    
### Implementations   
* 상품 정보를 전달하는 API 구성
* 상품 정보는 Database 에서 관리
* Cache 관리
  * Cache 적용이 필요한 Object 에 TTL 적용
  * Cache 적용이 필요한 Object 에 Cache hit 적용

---
