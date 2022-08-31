package com.alkemy.ong.core.usecase.impl;



import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.repository.MemberRepository;
import com.alkemy.ong.core.usecase.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public Long createEntity(Member member) {
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        memberRepository.findById(id).ifPresent(memberRepository::delete);
    }
}
