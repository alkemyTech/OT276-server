package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.AlkymerList;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.model.MemberList;
import org.springframework.data.domain.PageRequest;

public interface MemberService {


    Long createEntity(Member member);

    void updateEntityIfExists(Long id, Member member);

    void deleteById(Long id);

    MemberList getList(PageRequest pageRequest);
}
