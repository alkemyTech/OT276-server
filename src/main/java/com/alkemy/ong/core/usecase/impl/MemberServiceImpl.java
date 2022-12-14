package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.model.MemberList;
import com.alkemy.ong.core.repository.MemberRepository;
import com.alkemy.ong.core.usecase.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void updateEntityIfExists(Long id, Member member) {
        memberRepository.findById(id)
                .map(memberJpa -> {
                    memberJpa.setName(member.getName());
                    memberJpa.setFacebookUrl(member.getFacebookUrl());
                    memberJpa.setInstagramUrl(member.getInstagramUrl());
                    memberJpa.setLinkedinUrl(member.getLinkedinUrl());
                    memberJpa.setImage(member.getImage());
                    memberJpa.setDescription(member.getDescription());
                    return memberRepository.save(memberJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }


    public void deleteById(Long id) {
        memberRepository.findById(id).ifPresent(memberRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberList getList(PageRequest pageRequest) {
        Page<Member> page = memberRepository.findAll(pageRequest);
        return new MemberList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
