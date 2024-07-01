package org.example.publicdatacontest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/testd")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok("test success");
    }
}
