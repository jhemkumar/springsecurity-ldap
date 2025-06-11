package com.example.springbootldapsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the secured application!";
    }

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "This is a secured endpoint";
    }
} 