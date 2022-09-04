package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Optional<Slide> findFirstByOrderByIdDesc();
}
