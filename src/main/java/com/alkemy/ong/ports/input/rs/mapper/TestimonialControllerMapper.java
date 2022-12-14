package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TestimonialControllerMapper extends CommonMapper {

    Testimonial testimonialRequestToEntity(TestimonialRequest request);

    TestimonialResponse EntityToTestimonialResponse(Testimonial testimonial);

    List<TestimonialResponse> testimonialListToTResponse(List<Testimonial> testimonialList);

    Testimonial updateTestimonialToEntity(UpdateTestimonialRequest request);
}


