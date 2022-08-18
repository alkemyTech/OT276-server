package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Member;
import org.springframework.data.repository.CrudRepository;


public interface MemberRepository extends CrudRepository <Member, Long> {
}
