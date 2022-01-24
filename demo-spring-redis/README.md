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
```docker run -it [container] redis-cli -p 6379```    
* Reference: https://hub.docker.com/_/redis
* **Docker Compose Cluster (Master/Slaves) 설치**      
  * ```docker-compose.yml``` 파일 생성 
```yaml
version: '2'

networks:
  app-tier:
    driver: bridge

services:
  redis:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=master
      - ALLOW_EMPTY_PASSWORD=yes
    networks:
      - app-tier
    ports:
      - 6379:6379
  redis-slave-1:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=slave
      - REDIS_MASTER_HOST=redis
      - ALLOW_EMPTY_PASSWORD=yes
    ports:
      - 6479:6379
    depends_on:
      - redis
    networks:
      - app-tier
  redis-slave-2:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=slave
      - REDIS_MASTER_HOST=redis
      - ALLOW_EMPTY_PASSWORD=yes
    ports:
      - 6579:6379
    depends_on:
      - redis
    networks:
      - app-tier
  redis-commander:
    container_name: redis-commander
    hostname: redis-commander
    image: rediscommander/redis-commander:latest
    restart: always
    environment:
      - REDIS_HOSTS=redis:redis,redis-slave-1:redis-slave-1,redis-slave-2:redis-slave-2
    ports:
      - "8081:8081"
```      

* ```docker-compose up -d```  
* Reference: https://sup2is.github.io/2020/07/22/redis-replication-with-sentinel.html

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
