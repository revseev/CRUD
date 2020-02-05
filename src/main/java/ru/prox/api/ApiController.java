package ru.prox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.prox.model.Role;
import ru.prox.model.User;
import ru.prox.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    Role role;
    User user;
    List<Role> roles = new ArrayList<>();

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/all")
    public List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/api/edit")
    public ModelAndView edit(@RequestParam("id") Long id
                    , @RequestParam("login") String login
                    , @RequestParam("email") String email
                    , @RequestParam("password") String password
                    , @RequestParam("role") String sRole) {

        role = chooseRole(sRole);
        roles.add(role);
        user = userRepository.getOne(id);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        userRepository.save(user);
        roles.clear();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/control/admin");
        return modelAndView;
    }

    @PostMapping("/api/add")
    public ModelAndView add(@RequestParam("login") String login
                    , @RequestParam("email") String email
                    , @RequestParam("password") String password
                    , @RequestParam("role") String sRole) {
        role = chooseRole(sRole);
        roles.add(role);
        user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        userRepository.save(user);
        roles.clear();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/control/admin");
        return modelAndView;
    }

    public Role chooseRole(String sRole) {
        if (sRole.contains("ADMIN")) {
            role = new Role((long)1, "ROLE_ADMIN");
        } if (sRole.contains("USER")) {
            role = new Role((long)2, "ROLE_USER");
        }
        return role;
    }

}
