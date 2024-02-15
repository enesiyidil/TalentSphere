package org.group3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/manager")
    public ResponseEntity<String> fallbackAuth(){
        return ResponseEntity.ok("Auth Service out of service.");
    }

    @GetMapping("/user")
    public ResponseEntity<String> fallbackUser(){
        return ResponseEntity.ok("User Profile Service out of service.");
    }
}
