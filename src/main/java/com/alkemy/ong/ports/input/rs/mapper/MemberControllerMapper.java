package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MemberControllerMapper extends CommonMapper {

    Member createMemberRequestToMember(MemberRequest memberRequest);

    Member updateMemberRequestToMember(MemberRequest memberRequest);
}
