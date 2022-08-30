package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Member;

public interface MemberService {


    Long createEntity(Member member);

    void updateEntityIfExists(Long id, Member member);
}
