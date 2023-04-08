package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.InterfaceUserService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {
    private final InterfaceUserService userService;

    @Autowired
    public AdminController(InterfaceUserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin")
    public String findAll(Model model, Principal principal) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        model.addAttribute("principalUser", userService.findByUsername(principal.getName()));
        model.addAttribute("roleList", userService.getRoles());
        return "admin/admin-list";
    }

    @PostMapping("admin-create")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/profile")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin/profile";
    }
}
