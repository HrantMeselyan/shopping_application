package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto) {
        if (commentRequestDto == null){
            return null;
        }
        Comment comment = new Comment();
        comment.setComment(commentRequestDto.getComment());
        return comment;
    }

    public static List<CommentResponseDto> commentRequestDtoToComment(List<Comment> comments) {
        if (comments == null){
            return null;
        }
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        for (Comment comment : comments) {
            commentResponseDto.setComment(comment.getComment());
        }
        return commentResponseDtoList;
    }

}
