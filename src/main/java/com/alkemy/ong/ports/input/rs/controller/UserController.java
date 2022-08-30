package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.ports.input.rs.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
public class UserController  {


}
