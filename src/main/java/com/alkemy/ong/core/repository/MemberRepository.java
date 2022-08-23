package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
