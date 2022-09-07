package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.ConflictException;
import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.repository.TestimonialRepository;
import com.alkemy.ong.core.usecase.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository repositoryT;


    @Override
    public Long createNewTestimonial(Testimonial testimonial) {
        if (exist(testimonial.getName())) {
            throw new ConflictException("There is already testimonial with name: "+testimonial.getName());
        }
        return repositoryT.save(testimonial).getId();
    }


    private Boolean exist(String name){
    return repositoryT.existsByName(name);
    }

}

