package ru.prox.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.prox.model.Role;
import ru.prox.model.User;


import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    List<Role> roles = new ArrayList<>();
    HttpHeaders headers = new HttpHeaders();

    RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin","admin").build();


    @GetMapping("/api/all")
    public ResponseEntity <List<User>> all() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<User> userList = new ArrayList<>();
        HttpEntity<Object> requestEntity = new HttpEntity<>(userList,headers);
        return restTemplate.exchange("http://localhost:8081/api/all", HttpMethod.GET, requestEntity,new ParameterizedTypeReference<List<User>>() {});
    }


    @GetMapping("/api/getroles")
    public ResponseEntity <List<Role>> getRoles() {
        HttpEntity<Object> requestEntity = new HttpEntity<>(roles,headers);
        return restTemplate.exchange("http://localhost:8081/api/getroles", HttpMethod.GET, requestEntity,new ParameterizedTypeReference<List<Role>>() {});
    }

    @PostMapping("/api/edit")
    public User edit(@RequestBody User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/api/edit", HttpMethod.POST, request, User.class);
        return response.getBody();
    }

    @PostMapping("/api/add")
    public User add(@RequestBody User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/api/add", HttpMethod.POST, request, User.class);
        return response.getBody();
    }





}
