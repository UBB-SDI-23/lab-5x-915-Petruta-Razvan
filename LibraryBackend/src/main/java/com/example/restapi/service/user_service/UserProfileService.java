package com.example.restapi.service.user_service;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserDTO_Converters;
import com.example.restapi.dtos.userdtos.UserProfileDTO;
import com.example.restapi.exceptions.UserNotFoundException;
import com.example.restapi.exceptions.UserProfileNotFoundException;
import com.example.restapi.model.user.User;
import com.example.restapi.model.user.UserProfile;
import com.example.restapi.repository.user_repository.UserProfileRepository;
import com.example.restapi.repository.user_repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements IUserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserDTO_Converters.convertToUserDTO(user, this.modelMapper);
    }

    @Override
    public UserProfileDTO updateUserProfile(UserProfileDTO newUserProfile, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserProfile userProfileUpdated = userProfileRepository.findById(user.getUserProfile().getId())
                .map(userProfile -> {
                    userProfile.setBio(newUserProfile.getBio());
                    userProfile.setLocation(newUserProfile.getLocation());
                    userProfile.setGender(newUserProfile.getGender());
                    userProfile.setMaritalStatus(newUserProfile.getMaritalStatus());
                    userProfile.setBirthDate(newUserProfile.getBirthDate());
                    return userProfileRepository.save(userProfile);
                })
                .orElseThrow(() -> new UserProfileNotFoundException(id));

        return UserDTO_Converters.convertToUserProfileDTO(userProfileUpdated, this.modelMapper);
    }
}
