package com.example.shopping_application.dto.commentDto;

import com.example.shopping_application.dto.productDto.CreateProductRequestDto;
import com.example.shopping_application.dto.productDto.ProductDto;
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
public class CommentRequestDto {

    private String comment;
    private Date commentDateTime;
    private ProductDto productDto;
    private UserDto userDto;
    private CreateProductRequestDto productRequestDto;
}
