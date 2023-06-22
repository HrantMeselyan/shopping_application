package com.example.shopping_application.dto.commentDto;

import com.example.shopping_application.dto.userDto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private String comment;
    private UserDto userDto;
    private Date commentDateTime;
}
