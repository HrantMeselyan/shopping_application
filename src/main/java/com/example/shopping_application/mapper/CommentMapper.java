package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.commentDto.CommentDto;
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
            commentResponseDto.setUserDto(UserMapper.userToUserDto(comment.getUser()));
        }
        return commentResponseDtoList;
    }
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setCommentDateTime(comment.getDateTime());
        commentDto.setUser(UserMapper.userToUserDto(comment.getUser()));
        commentDto.setProduct(comment.getProduct());
        return commentDto;
    }
}
