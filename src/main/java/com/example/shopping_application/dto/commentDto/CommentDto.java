package com.example.shopping_application.dto.commentDto;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private int id;
    private String comment;
    private Date commentDateTime;
    private User user;
    private Product product;
}
