package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.usecase.impl.MemberServiceImpl;
import com.alkemy.ong.ports.input.rs.api.MemberApi;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapper;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.MEMBERS_URI;

@RestController
@RequestMapping(MEMBERS_URI)
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberControllerMapper mapper;
    private  final MemberServiceImpl memberService;

    @Override
    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest memberRequest) {

        Member member = mapper.createMemberRequestToMember(memberRequest);

        final long id = memberService.createEntity(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upDateMember(@NotNull @PathVariable Long id, @Valid @RequestBody MemberRequest MemberRequest){
        Member member = mapper.updateMemberRequestToMember(MemberRequest);
        memberService.updateEntityIfExists(id, member);
    }
}
