package com.alkemy.ong.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.core.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {

    Boolean existsByName(String name);


}