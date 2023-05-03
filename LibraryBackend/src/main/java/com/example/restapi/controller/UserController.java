package com.example.restapi.controller;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserProfileDTO;
import com.example.restapi.dtos.userdtos.UserRolesDTO;
import com.example.restapi.dtos.userdtos.UserWithRolesDTO;
import com.example.restapi.service.user_service.IUserProfileService;
import com.example.restapi.service.user_service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@CrossOrigin(origins = "https://sdi-library-management.netlify.app", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    private final IUserProfileService userProfileService;
    private final UserService userService;

    UserController(IUserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @GetMapping("/user-profile/{username}")
    UserDTO getProfile(@PathVariable String username) {
        return this.userProfileService.getUserProfile(username);
    }

    @GetMapping("/users-search")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserWithRolesDTO>> getUsersByUsername(@RequestParam(required = false) String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.getTop20UsersByUsername(username));
    }

    @PutMapping("/user-profile/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    UserProfileDTO updateUser(@Valid @RequestBody UserProfileDTO newUserProfile, @PathVariable String username) {
        return this.userProfileService.updateUserProfile(newUserProfile, username);
    }

    @PutMapping("/user-update-roles/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    UserWithRolesDTO updateUserRoles(@RequestBody UserRolesDTO roles, @PathVariable String username) {
        return this.userService.updateUserRoles(username, roles);
    }
}
