package org.example.publicdatacontest.controller;

import lombok.AllArgsConstructor;
import org.example.publicdatacontest.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/class")
    public ResponseEntity<?> likeClass(@RequestParam(value = "classId") Long classId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(likeService.likeClass(classId, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/class")
    public ResponseEntity<?> deleteLike(@RequestParam(value = "classId") Long classId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(likeService.deleteLike(classId, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/class")
    public ResponseEntity<?> getLikeClassList(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(likeService.getLikeClassList(userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
