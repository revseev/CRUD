package ru.prox.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.prox.model.Role;
import ru.prox.model.User;
import ru.prox.service.RestService;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    RestService restService;

    @GetMapping("/rest/all")
    public ResponseEntity <List<User>> all() {
        return restService.all();
    }


    @GetMapping("/rest/getroles")
    public ResponseEntity <List<Role>> getRoles() {
        return restService.getRoles();
    }

    @PostMapping("/rest/edit")
    public User edit(@RequestBody User user) {
        return restService.edit(user);
    }

    @PostMapping("/rest/add")
    public User add(@RequestBody User user) {
        return restService.add(user);
    }
}
