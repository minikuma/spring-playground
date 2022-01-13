package me.minikuma.example.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@EqualsAndHashCode(callSuper = false)
@RedisHash(value = "id", timeToLive = 10)
public class User {

    private static final long serialVersionUID = 1370692830319429806L;

    @Id
    private final String id;
    private final String name;
    private final Integer age;
    private final String address;

    @Builder
    public User(String id, String name, Integer age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
