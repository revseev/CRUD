package ru.prox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.prox.model.Role;
import ru.prox.model.User;
import ru.prox.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    User user;
    List<Role> roles = new ArrayList<>();
    Role role = null;

    @GetMapping("/control/admin")
    public ModelAndView allUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        user = userRepository.findUserByLogin(authUserName);
        List<User> users = userRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("control/admin");
        modelAndView.addObject("userList", users).addObject("user", user);
        return modelAndView;
    }
    

    @PostMapping("/control/edit")
    public ModelAndView edit(@ModelAttribute("user") User user, @ModelAttribute("role") String sRole) {
        if (sRole.contains("ADMIN")) {
            role = new Role((long)1, "ROLE_ADMIN");
        } if (sRole.contains("USER")) {
            role = new Role((long)2, "ROLE_USER");
        }
        roles.clear();
        roles.add(role);
        User userFromDB = userRepository.getOne(user.getId());
        userFromDB.setRoles(roles);
        userFromDB.setLogin(user.getLogin());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userRepository.save(userFromDB);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/control/admin");
        return modelAndView;
    }

    @PostMapping("/control/add")
    public ModelAndView addUser(@ModelAttribute("user") User user, @ModelAttribute("role") String sRole) {
        if (sRole.contains("ADMIN")) {
            role = new Role((long)1, "ROLE_ADMIN");
        } if (sRole.contains("USER")) {
            role = new Role((long)2, "ROLE_USER");
        }
        roles.clear();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/control/admin");
        return modelAndView;
    }

    @GetMapping("/control/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/control/admin");
        User user = userRepository.getOne(id);
        userRepository.delete(user);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
