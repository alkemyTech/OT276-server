package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.security.JwtUtils;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {


    private MockMvc mockMvc;

    private Collection authorities;
    @InjectMocks
    AuthController controller;
    @Mock
    UserService service;
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;
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
        authorities = Collections.emptyList();
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@test.com")
                .password("test123")
                .firstName("test")
                .lastName("test")
                .photo("img")
                .build();


        User user = mapper.createUserRequestToUser(request);
        user.setId(99L);

        given(service.createEntity(any(User.class))).willReturn(99L);
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, "test", authorities));
        when(jwtUtils.generateToken(any(UserDetails.class))).thenReturn("token");
        when(jwtUtils.extractExpiration(any())).thenReturn(new Date());

        String content = mockMvc.perform(
                        post(ApiConstants.AUTHENTICATION_URI + "/register")

                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();
    }


    @Test
    void login_shouldReturn201() throws Exception {
        authorities = Collections.emptyList();
        LoginRequest request = LoginRequest.builder()
                .userName("jdoe@somosmas.org")
                .password("jdoe123")
                .build();

        User user = new User();
        user.setId(2L);
        user.setEmail(request.getUserName());


        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(new UsernamePasswordAuthenticationToken(
                user, request.getPassword(), authorities));
        when(jwtUtils.generateToken(any(UserDetails.class))).thenReturn("token");
        when(jwtUtils.extractExpiration(any())).thenReturn(new Date());

        String content = mockMvc.perform(
                        post(ApiConstants.AUTHENTICATION_URI + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();


    }

    @Test
    void me_shouldReturn201() throws Exception {
        User user = new User();
        user.setId(2L);

        String content = mockMvc.perform(get(ApiConstants.AUTHENTICATION_URI + "/me"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();
        UserResponse response = mapper.userToUserResponse(user);
        assertThat(response.getId()).isEqualTo(2);
    }

}