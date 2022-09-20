package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface CommentControllerMapper extends CommonMapper {

    @Named("createCommentRequestToComment")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    @Mapping(target = "user", source = "user")
    Comment createCommentRequestToComment(CreateCommentRequest createCommentRequest, User user);

    @Named("createCommentRequestToComment")
    @Mapping(target = "audit", ignore = true)
    Comment createCommentRequestToComment(CreateCommentRequest createCommentRequest);

    @Named("commentToCommentResponse")
    CommentResponse commentToCommentResponse(Comment comment);

    @IterableMapping(qualifiedByName = "commentToCommentResponse")
    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);
}
