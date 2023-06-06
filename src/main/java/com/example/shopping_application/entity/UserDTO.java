package com.example.shopping_application.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Ashot Simonyan on 04.06.23.
 */

@Data
public class UserDTO {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String postCode;
    private String profilePic;
    private Gender gender;
    private List<Address> addresses;
    private Role role;
}
