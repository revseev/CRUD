package ru.prox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prox.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
}
