package com.example.shopping_application.dto.userDto;

import com.example.shopping_application.dto.addressDto.AddressDto;
import com.example.shopping_application.entity.Address;
import com.example.shopping_application.entity.Gender;
import com.example.shopping_application.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Ashot Simonyan on 08.06.23.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String postCode;
    private String profilePic;
    private Role role;
    private Gender gender;
    private List<AddressDto> addresses;
}
