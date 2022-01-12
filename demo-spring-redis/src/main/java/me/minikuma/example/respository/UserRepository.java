package me.minikuma.example.respository;

import me.minikuma.example.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
