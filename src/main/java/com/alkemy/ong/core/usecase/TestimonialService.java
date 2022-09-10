package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.repository.TestimonialRepository;

public interface TestimonialService {

    Long createNewTestimonial(Testimonial testimonial);
}
