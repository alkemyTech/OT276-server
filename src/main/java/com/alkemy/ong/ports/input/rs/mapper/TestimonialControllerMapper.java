package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;

import org.mapstruct.Mapper;


@Mapper
public interface TestimonialControllerMapper extends  CommonMapper {

    Testimonial testimonialRequestToEntity(TestimonialRequest request);

}


