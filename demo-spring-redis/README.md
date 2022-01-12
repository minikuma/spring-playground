## Spring Boot Redis Connection    

--- 

Spring 에서 지원하고 있는 여러 Redis Connection 방법과 그에 따른 설정과 테스트를 한 샘플 소스 입니다.   

이 저장소에는 아래 내용이 포함되어 있습니다.

* LettuceConnection
  * Stand Alone
  * Master-Replica
* JedisConnection
  * Sentinel
* RedisTemplate 을 통한 Object 작업  
* Redis Repositories 을 통한 Object 작업

---
### Installation   
* Docker 를 통한 Redis 설치    
```docker run -p 6379:6379 --name some-redis -d redis```
* Redis Cli 접근      
```docker run -it --link some-redis:redis --rm redis redis-cli -h redis -p 6379```    
* Reference: https://hub.docker.com/_/redis

---

### Using Redis   

* Redis Repositories    
* RedisTemplate

---
