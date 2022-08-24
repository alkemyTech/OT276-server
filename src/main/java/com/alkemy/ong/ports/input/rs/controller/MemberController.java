package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.usecase.impl.MemberServiceImpl;
import com.alkemy.ong.ports.input.rs.mapper.AlkymerControllerMapper;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.MEMBERS_URI;

@RestController
@RequestMapping(MEMBERS_URI)
@RequiredArgsConstructor
public class MemberController {

    private final AlkymerControllerMapper mapper;
    private  final MemberServiceImpl memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody MemberRequest memberRequest) {

        Member member = mapper.createMemberRequestToMember(memberRequest);

        final long id = memberService.createEntity(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
