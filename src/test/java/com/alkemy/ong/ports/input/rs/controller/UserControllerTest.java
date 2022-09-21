package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Role;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    UserController controller;

    @Mock
    UserService service;

    @Spy
    UserControllerMapper mapper = Mappers.getMapper(UserControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void getUsers_shouldReturn200() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("jdoe@somosmas.org");
        user.setPassword("$2a$10$npmFpCbBFdtXjGGhgc1ste1quwGaF4MvkgYk9N2rBDFkOrV5Lj6M6");
        user.setPhoto("https://static-url.com/avatar.jpg");
        user.setRole(new Role());
        user.getRole().setId(2L);

        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jsmmith@somosmas.org");
        user2.setPassword("$2a$10$npmFpCbBFdtXjGGhgc1ste1quwGaF4MvkgYk9N2rBDFkOrV5Lj6M6");
        user2.setPhoto("https://static-url.com/avatar.jpg");
        user2.setRole(new Role());
        user2.getRole().setId(2L);

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);

        given(service.getList()).willReturn(list);

        String content = mockMvc.perform(get(ApiConstants.USERS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<UserResponse> response = JsonUtils.jsonToCollection(content, List.class, UserResponse.class);

        UserResponse userResponse = mapper.userToUserResponse(user);

        assertThat(response.isEmpty()).isFalse();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0)).isEqualTo(userResponse);
    }

    @Test
    void updateUser_shouldReturn204() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()
                .password("12345678")
                .build();

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void updateUser_shouldReturn400() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()

                .password("12678")
                .build();

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateUser_shouldReturn404() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()
                .build();

        doThrow(new NotFoundException(10)).when(service).updateEntityIfExists(eq(10L), any(User.class));

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))

                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteUser_shouldReturn204() throws Exception {

        mockMvc.perform(delete(ApiConstants.USERS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
