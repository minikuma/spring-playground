package me.minikuma.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("User")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String address;
}
