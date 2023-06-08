package com.example.shopping_application.dto.userDto;

import com.example.shopping_application.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ashot Simonyan on 08.06.23.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    private String name;
    private String surname;
    private String email;
    private Gender gender;
}
