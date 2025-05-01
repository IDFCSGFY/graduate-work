package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.User;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/login")
    public ResponseEntity<String> userLogin(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok("Successful operation");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> userLogout() {
        return ResponseEntity.ok("Successful operation");
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(new User());
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User user) {
        return ResponseEntity.ok("Successful operation");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return ResponseEntity.ok("User deleted");
    }
}
