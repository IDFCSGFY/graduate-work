package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.impl.UserProfileService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProfileService service;

    public UserController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        if (service.changePassword(newPassword)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> findAuthenticatedUser(Authentication authentication) {
        return ResponseEntity.ok(service.getUserDTOByUsername(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(Authentication authentication, @RequestBody UpdateUser user) {
        return ResponseEntity.ok(service.updateUserByUsername(authentication.getName(), user));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAuthorizedUserAvatar(@RequestParam MultipartFile avatar) {
        return ResponseEntity.ok().build();
        //
        //
        //
        //
        //
        //
        //
        //
        //
    }
}
