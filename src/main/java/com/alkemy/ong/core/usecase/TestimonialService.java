package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Testimonial;


public interface TestimonialService {

    Long createNewTestimonial(Testimonial testimonial);

    void deleteTestimonial(long id);

}
