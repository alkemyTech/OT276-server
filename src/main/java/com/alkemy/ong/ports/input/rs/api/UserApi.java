package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface UserApi {

    ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user)

}
