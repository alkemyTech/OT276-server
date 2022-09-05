package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.AlkymerList;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.model.MemberList;
import com.alkemy.ong.core.usecase.MemberService;
import com.alkemy.ong.core.usecase.impl.MemberServiceImpl;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.MemberApi;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapper;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponse;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.MEMBERS_URI;

@RestController
@RequestMapping(MEMBERS_URI)
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberControllerMapper mapper;
    private  final MemberService memberService;

    @Override
    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest memberRequest) {

        Member member = mapper.memberRequestToMember(memberRequest);

        final long id = memberService.createEntity(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upDateMember(@NotNull @PathVariable Long id, @Valid @RequestBody MemberRequest MemberRequest) {
        Member member = mapper.memberRequestToMember(MemberRequest);
        memberService.updateEntityIfExists(id, member);
    }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteMember(@NotNull @PathVariable Long id){
            memberService.deleteById(id);

        }

    @GetMapping
    public ResponseEntity<MemberResponseList> getMember(@RequestParam Optional<Integer> page,
                                                        @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        MemberList list = memberService.getList(PageRequest.of(pageNumber, pageSize));

        MemberResponseList response;
        {
            response = new MemberResponseList();

            List<MemberResponse> content = mapper.memberListToMemberResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }
}
