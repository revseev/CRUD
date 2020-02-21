package ru.prox.rest;


import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.prox.handlers.LoginSuccessHandler;
import ru.prox.model.Role;
import ru.prox.model.User;
import ru.prox.service.GoogleService;
import ru.prox.service.RestService;
import ru.prox.service.UserService;
import ru.prox.utils.SocialUltils;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@org.springframework.web.bind.annotation.RestController
public class RestController {
    Map<String, Object> map = new HashMap<>();
    @Autowired
    RestService restService;
    @Autowired
    UserService userService;

    SocialUltils socialUltils = new SocialUltils();

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

    @GetMapping("/callback")
    public Map<String, Object> callback(@RequestParam("code") String code) throws IOException, InterruptedException, ExecutionException {
        code = URLDecoder.decode(code, "UTF-8");
        OAuth2AccessToken accessToken = GoogleService.getOAuth20GoogleService().getAccessToken(code);
        OAuthRequest request = new OAuthRequest(Verb.GET, GoogleService.PROTECTED_RESOURCE_URL);
        GoogleService.getOAuth20GoogleService().signRequest(accessToken, request);
        try (Response response = GoogleService.getOAuth20GoogleService().execute(request)) {
            List<Role> roles = new ArrayList<>();
            Role role = new Role((long) 1, "ROLE_ADMIN");
            roles.add(role);
            User user = new User();
            map = new Gson().fromJson(response.getBody(), Map.class);
            String email = (String) map.get("email");
            String login = socialUltils.getLoginFromEmail(email);
            user.setLogin(login);
            user.setEmail(email);
            user.setRoles(roles);
            if (restService.getUserByLogin(login) == null) {
                restService.add(user);
                map = new Gson().fromJson(response.getBody(), Map.class);
            }
            System.out.println(map);
            return map;
        }
    }

}
