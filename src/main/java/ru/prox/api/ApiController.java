package ru.prox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prox.model.User;
import ru.prox.repository.UserRepository;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/all")
    public List<User> all() {
        return userRepository.findAll();
    }
}
