package com.example.restapi.service.user_service;

import com.example.restapi.dtos.userdtos.UserDTO;
import com.example.restapi.dtos.userdtos.UserDTO_Converters;
import com.example.restapi.dtos.userdtos.UserProfileDTO;
import com.example.restapi.exceptions.UserNotFoundException;
import com.example.restapi.exceptions.UserProfileNotFoundException;
import com.example.restapi.model.user.User;
import com.example.restapi.model.user.UserProfile;
import com.example.restapi.repository.BookRepository;
import com.example.restapi.repository.ReaderRepository;
import com.example.restapi.repository.library_repository.LibraryRepository;
import com.example.restapi.repository.user_repository.UserProfileRepository;
import com.example.restapi.repository.user_repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements IUserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final ModelMapper modelMapper;

    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository, LibraryRepository libraryRepository, BookRepository bookRepository, ReaderRepository readerRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Long totalLibraries = this.libraryRepository.countByUserID(user.getID());
        Long totalBooks = this.bookRepository.countByUserID(user.getID());
        Long totalReaders = this.readerRepository.countByUserID(user.getID());
        return UserDTO_Converters.convertToUserDTO(user, this.modelMapper, totalLibraries, totalBooks, totalReaders);
    }

    @Override
    public UserProfileDTO updateUserProfile(UserProfileDTO newUserProfile, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        UserProfile userProfileUpdated = userProfileRepository.findById(user.getUserProfile().getId())
                .map(userProfile -> {
                    userProfile.setBio(newUserProfile.getBio());
                    userProfile.setLocation(newUserProfile.getLocation());
                    userProfile.setGender(newUserProfile.getGender());
                    userProfile.setMaritalStatus(newUserProfile.getMaritalStatus());
                    userProfile.setBirthDate(newUserProfile.getBirthDate());
                    return userProfileRepository.save(userProfile);
                })
                .orElseThrow(() -> new UserProfileNotFoundException(user.getUserProfile().getId()));

        return UserDTO_Converters.convertToUserProfileDTO(userProfileUpdated, this.modelMapper);
    }
}
