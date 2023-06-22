package com.example.shopping_application.dto.commentDto;

import com.example.shopping_application.dto.userDto.UserDto;
import com.example.shopping_application.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private int id;
    private String comment;
    private LocalDateTime commentDateTime;
    private UserDto user;
    private Product product;
}
