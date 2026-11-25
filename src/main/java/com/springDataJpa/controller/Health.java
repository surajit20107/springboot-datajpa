package com.springDataJpa.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Health {
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
    
}
