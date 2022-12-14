package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.model.TestimonialList;
import org.springframework.data.domain.PageRequest;


public interface TestimonialService {

    Long createNewTestimonial(Testimonial testimonial);

    void deleteTestimonial(long id);


    TestimonialList getList(PageRequest request);

    void updateTestimonialIfExist(long id, Testimonial testimonial);


}
