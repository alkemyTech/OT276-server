package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {


    private MockMvc mockMvc;

    @InjectMocks
    AuthController controller;
    @Mock
    UserService service;

    @Mock
    AuthenticationManager authenticationManager;


    @Spy
    UserControllerMapper mapper = Mappers.getMapper(UserControllerMapper.class);


    @BeforeEach
    void setUp() {


        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();

    }

    @Test
    void register_shouldReturn201() throws Exception {

        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@test.com")
                .password("test123")
                .firstName("test")
                .lastName("test")
                .photo("img")
                .build();

        mockMvc.perform(
                        post(ApiConstants.AUTHENTICATION_URI + "/register")

                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated());


    }


    @Test
    void login_shouldReturn201() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .userName("jdoe@somosmas.org")
                .password("jdoe123")
                .build();


        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(new UsernamePasswordAuthenticationToken(
                request.getUserName(), request.getPassword()));

        mockMvc.perform(
                        post(ApiConstants.AUTHENTICATION_URI + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk());


    }
}