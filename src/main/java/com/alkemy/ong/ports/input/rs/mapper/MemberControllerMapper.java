package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MemberControllerMapper extends CommonMapper {

    Member memberRequestToMember(MemberRequest memberRequest);

    List<MemberResponse> memberListToMemberResponseList(List<Member> members);
}
