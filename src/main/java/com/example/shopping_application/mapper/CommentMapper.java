package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.entity.Comment;
import com.example.shopping_application.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment map(CommentRequestDto commentRequestDto, User user) {
        Comment comment = new Comment();
        comment.setComment(commentRequestDto.getComment());
        comment.setUser(user);
        return comment;
    }

    public static List<CommentResponseDto> map(List<Comment> comments) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        for (Comment comment : comments) {
            commentResponseDto.setComment(comment.getComment());
        }
        return commentResponseDtoList;
    }

}
