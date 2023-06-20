package com.example.shopping_application.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String profilePic;
}
