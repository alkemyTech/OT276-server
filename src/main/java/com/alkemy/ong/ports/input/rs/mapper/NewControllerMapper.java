package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NewControllerMapper extends CommonMapper{
    @Mapping(target = "id", ignore = true)
    New createNewRequestToNew(CreateNewRequest createNewRequest);
}
