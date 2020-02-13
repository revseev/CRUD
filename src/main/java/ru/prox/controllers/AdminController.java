package ru.prox.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ru.prox.model.User;

@Controller
public class AdminController {


    RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin","admin").build();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/control/admin")
    public ModelAndView allUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(authUserName,headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/api/getUser", HttpMethod.POST, requestEntity, User.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("control/admin");
        modelAndView.addObject("user", response.getBody());
        return modelAndView;
    }


    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
