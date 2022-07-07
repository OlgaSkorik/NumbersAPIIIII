package com.example.numbers.controller;

import com.example.numbers.entity.User;
import com.example.numbers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> save (@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        Optional<User> byUsername = userService.findByUsername(username);
        return byUsername.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser (@PathVariable String username, @RequestBody User myNewUser) {
        Optional<User> myUser = userService.findByUsername(username);
        if (myUser.isPresent()) {
            myNewUser.setId(myUser.get().getId());
            User updateUser = userService.save(myNewUser);
            return ResponseEntity.ok(updateUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
