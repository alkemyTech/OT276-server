package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Member;
import org.springframework.http.ResponseEntity;

public interface MemberService {


    Long createEntity(Member member);
    void deleteById(Long id);
}
