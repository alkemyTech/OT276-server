package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserControllerMapper extends CommonMapper{

    User createUserRequestToUser(CreateUserRequest userRequest);

    UserResponse userToUserResponse(User user);
}
