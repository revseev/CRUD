package ru.prox.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import ru.prox.model.Role;
import ru.prox.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestService {

    List<Role> roles = new ArrayList<>();
    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin","admin").build();

    public ResponseEntity <List<User>> all() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<User> userList = new ArrayList<>();
        HttpEntity<Object> requestEntity = new HttpEntity<>(userList,headers);
        return restTemplate.exchange("http://localhost:8081/rest/all", HttpMethod.GET, requestEntity,new ParameterizedTypeReference<List<User>>() {});
    }

    public ResponseEntity <List<Role>> getRoles() {
        HttpEntity<Object> requestEntity = new HttpEntity<>(roles,headers);
        return restTemplate.exchange("http://localhost:8081/rest/getroles", HttpMethod.GET, requestEntity,new ParameterizedTypeReference<List<Role>>() {});
    }

    public User edit(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/rest/edit", HttpMethod.POST, request, User.class);
        return response.getBody();
    }

    public User add(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/rest/add", HttpMethod.POST, request, User.class);
        return response.getBody();
    }

    public User getUserByLogin(String login) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(login,headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/rest/getUser", HttpMethod.POST, requestEntity, User.class);
        return response.getBody();
    }
}
