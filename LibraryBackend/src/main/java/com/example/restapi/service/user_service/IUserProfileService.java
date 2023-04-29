package com.example.restapi.service.user_service;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserProfileDTO;

public interface IUserProfileService {
    UserDTO getUserProfile(Long id);

    UserProfileDTO updateUserProfile(UserProfileDTO newUserProfile, Long id);
}
