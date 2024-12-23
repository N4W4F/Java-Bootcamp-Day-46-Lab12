package com.example.blogsystem.Controller;

import com.example.blogsystem.ApiResponse.ApiException;
import com.example.blogsystem.Model.MyUser;
import com.example.blogsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal MyUser myUser) {
        return  ResponseEntity.status(200).body(authService.getAllUsers(myUser.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid MyUser myUser) {
        authService.register(myUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiException("User has been registered successfully"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@AuthenticationPrincipal MyUser myUser,
                                     @PathVariable Integer userId,
                                     @RequestBody @Valid MyUser user) {
        authService.updateUser(myUser.getId(), userId, user);
        return ResponseEntity.status(200).body(new ApiException("User with ID: " + userId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{userId}")
    public  ResponseEntity deleteUser(@AuthenticationPrincipal MyUser myUser,
                                      @PathVariable Integer userId) {
        authService.deleteUser(myUser.getId(), userId);
        return ResponseEntity.status(200).body(new ApiException("User with ID: " + userId + " has been deleted successfully"));
    }
}
