package com.bus.ticketing.controller;

import com.bus.ticketing.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{identification}")
    public ResponseEntity readUser(@PathVariable("identification") String identification) {
        log.info("Reading user with identification: {}", identification);
        return ResponseEntity.ok(userService.readUser(identification));
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity readUserByEmail(@PathVariable("email") String email) {
        log.info("Reading user with email: {}", email);
        return ResponseEntity.ok(userService.readUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity readUsers(Pageable pageable) {
        log.info("Reading all users with pagination");
        return ResponseEntity.ok(userService.readUsers(pageable));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody com.bus.ticketing.model.dto.User user) {
        log.info("Creating new user: {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody com.bus.ticketing.model.dto.User user) {
        log.info("Updating user with ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}