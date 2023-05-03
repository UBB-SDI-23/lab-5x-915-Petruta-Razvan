package com.example.restapi.controller;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserProfileDTO;
import com.example.restapi.dtos.userdtos.UserRolesDTO;
import com.example.restapi.dtos.userdtos.UserWithRolesDTO;
import com.example.restapi.model.user.User;
import com.example.restapi.security.jwt.JwtUtils;
import com.example.restapi.service.user_service.IUserProfileService;
import com.example.restapi.service.user_service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@CrossOrigin(origins = "https://sdi-library-management.netlify.app", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    private final IUserProfileService userProfileService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    UserController(IUserProfileService userProfileService, UserService userService, JwtUtils jwtUtils) {
        this.userProfileService = userProfileService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/user-profile/{username}")
    UserDTO getProfile(@PathVariable String username) {
        return this.userProfileService.getUserProfile(username);
    }

    @GetMapping("/users-search")
    ResponseEntity<List<UserWithRolesDTO>> getUsersByUsername(@RequestParam(required = false) String username,
                                                              @RequestHeader("Authorization") String token) {
        String _username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(_username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.getTop20UsersByUsername(username, user.getID()));
    }

    @PutMapping("/user-profile/{username}")
    UserProfileDTO updateUser(@Valid @RequestBody UserProfileDTO newUserProfile,
                              @PathVariable String username,
                              @RequestHeader("Authorization") String token) {
        String _username = this.jwtUtils.getUserNameFromJwtToken(token);

        return this.userProfileService.updateUserProfile(newUserProfile, username, _username);
    }

    @PutMapping("/user-update-roles/{username}")
    UserWithRolesDTO updateUserRoles(@RequestBody UserRolesDTO roles,
                                     @PathVariable String username,
                                     @RequestHeader("Authorization") String token) {
        String _username = this.jwtUtils.getUserNameFromJwtToken(token);

        return this.userService.updateUserRoles(username, roles, _username);
    }
}
