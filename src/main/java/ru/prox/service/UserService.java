package ru.prox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.prox.model.Role;
import ru.prox.model.User;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    HttpHeaders headers = new HttpHeaders();

    RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin","admin").build();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(login,headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8081/api/getUser", HttpMethod.POST, requestEntity, User.class);
        if (response.getBody() == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(response.getBody().getLogin(),
                response.getBody().getPassword(),
                mapRolesToAuthorities(response.getBody().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}

