package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.ports.input.rs.response.NewCommentsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface NewControllerMapper extends CommonMapper {

    @Named("newToNewCommentsResponse")
    NewCommentsResponse newToNewCommentsResponse(New _new);
}
