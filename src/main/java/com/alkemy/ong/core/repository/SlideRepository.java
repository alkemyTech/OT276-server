package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlideRepository extends JpaRepository<Slide, Long> {

    List<Slide> findByOrganizationIdOrderByOrder(Long id);

}
