package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.model.TestimonialList;
import com.alkemy.ong.core.repository.TestimonialRepository;
import org.springframework.data.domain.PageRequest;

public interface TestimonialService {

    Long createNewTestimonial(Testimonial testimonial);

    void deleteTestimonial(long id);

    TestimonialList getList(PageRequest request);
}
