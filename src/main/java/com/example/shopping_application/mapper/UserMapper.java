package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.userDto.UserDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.dto.userDto.UserShortDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;

/**
 * Created by Ashot Simonyan on 08.06.23.
 */

public class UserMapper {

    public static User userRegisterDtoToUser(UserRegisterDto userRegisterDto) {
        if (userRegisterDto == null) {
            return null;
        }
        User user = new User();
        user.setName(userRegisterDto.getName());
        user.setSurname(userRegisterDto.getSurname());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        user.setGender(userRegisterDto.getGender());
        return user;
    }

    public static User userShortDtoToUser(UserShortDto userShortDto) {
        if (userShortDto == null) {
            return null;
        }
        User user = new User();
        user.setName(userShortDto.getName());
        user.setSurname(userShortDto.getSurname());
        user.setEmail(userShortDto.getEmail());
        user.setGender(userShortDto.getGender());
        return user;
    }

    public static User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPostCode(userDto.getPostCode());
        user.setProfilePic(userDto.getProfilePic());
        user.setRole(userDto.getRole());
        user.setGender(userDto.getGender());
        user.setAddresses(userDto.getAddresses());
        return user;
    }

    public static UserRegisterDto userToUserRegisterDto(User user) {
        if (user == null) {
            return null;
        }
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setName(user.getName());
        userRegisterDto.setSurname(user.getSurname());
        userRegisterDto.setEmail(user.getEmail());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setGender(user.getGender());
        return userRegisterDto;
    }

    public static UserShortDto userToUserShortDto(User user) {
        if (user == null) {
            return null;
        }
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setName(user.getName());
        userShortDto.setSurname(user.getSurname());
        userShortDto.setEmail(user.getEmail());
        userShortDto.setGender(user.getGender());
        return userShortDto;
    }

    public static UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPostCode(user.getPostCode());
        userDto.setProfilePic(user.getProfilePic());
        userDto.setRole(user.getRole());
        userDto.setGender(user.getGender());
        userDto.setAddresses(user.getAddresses());
        return userDto;
    }

    public static User currentUserToUser (CurrentUser currentUser){
        if (currentUser != null) {
            return currentUser.getUser();
        }
        return null;
    }
}
