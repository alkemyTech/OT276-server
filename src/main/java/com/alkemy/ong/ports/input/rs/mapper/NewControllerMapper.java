package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface NewControllerMapper extends CommonMapper {

    @Named("commentListToCommentResponseList")
    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);

    New createNewRequestToNew(CreateNewRequest createNewRequest);

    @Named("CategoryToCategoryResponse")
    @Mapping(source = "category.name", target = "category")
    NewResponse newToNewResponse(New news);

    @IterableMapping(qualifiedByName = "CategoryToCategoryResponse")
    List<NewResponse> newListToNewResponseList(List<New> newList);
}
