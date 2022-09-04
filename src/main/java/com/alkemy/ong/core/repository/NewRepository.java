package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewRepository extends JpaRepository<New, Long> {
}
