package com.example.restapi.controller;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserProfileDTO;
import com.example.restapi.service.user_service.IUserProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final IUserProfileService userProfileService;

    UserController(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/user-profile/{id}")
    UserDTO getProfile(@PathVariable Long id) {
        return this.userProfileService.getUserProfile(id);
    }

    @PutMapping("/user-profile/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    UserProfileDTO updateUser(@Valid @RequestBody UserProfileDTO newUserProfile, @PathVariable Long id) {
        return this.userProfileService.updateUserProfile(newUserProfile, id);
    }

}
