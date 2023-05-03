package com.example.restapi.service.user_service;

import com.example.restapi.dtos.userdtos.UserRolesDTO;
import com.example.restapi.dtos.userdtos.UserWithRolesDTO;
import com.example.restapi.exceptions.UserNotFoundException;
import com.example.restapi.model.user.ERole;
import com.example.restapi.model.user.Role;
import com.example.restapi.model.user.User;
import com.example.restapi.repository.user_repository.RoleRepository;
import com.example.restapi.repository.user_repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserWithRolesDTO updateUserRoles(String username, UserRolesDTO roles) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Set<Role> newRoles = new HashSet<>();

        if (roles.getRoles().contains("ROLE_USER")) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: User role not found."));
            newRoles.add(userRole);
        }

        if (roles.getRoles().contains("ROLE_MODERATOR")) {
            Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: User role not found."));
            newRoles.add(userRole);
        }

        if (roles.getRoles().contains("ROLE_ADMIN")) {
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: User role not found."));
            newRoles.add(userRole);
        }

        user.setRoles(newRoles);
        this.userRepository.save(user);

        return new UserWithRolesDTO(user.getUsername(), user.getRoles());
    }

    public List<UserWithRolesDTO> getTop20UsersByUsername(String searchTerm) {
        return this.userRepository.findTop20BySearchTerm(searchTerm).stream().map(user -> {
            UserWithRolesDTO userDTO = new UserWithRolesDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setRoles(user.getRoles());
            return userDTO;
        }).collect(Collectors.toList());
    }
}
