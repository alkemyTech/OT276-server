package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    List<Slide> findByOrganizationIdOrderByOrder(Long id);

    @Query(value = "select max(slide_order) + 1 from slide", nativeQuery = true)
    Integer findNextMaxSlideOrder();
}
