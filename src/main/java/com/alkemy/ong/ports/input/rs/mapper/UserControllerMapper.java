package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserControllerMapper extends CommonMapper {

    User createUserRequestToUser(CreateUserRequest userRequest);

    @Named("userToUserResponse")
    UserResponse userToUserResponse(User user);

    @Named("userListToUserResponseList")
    @IterableMapping(qualifiedByName = "userToUserResponse")
    List<UserResponse> userListToUserResponseList(List<User> users);
}
