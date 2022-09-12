package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.ConflictException;
import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.model.TestimonialList;
import com.alkemy.ong.core.repository.TestimonialRepository;
import com.alkemy.ong.core.usecase.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;


    @Override
    @Transactional
    public Long createNewTestimonial(Testimonial testimonial) {
        if (exist(testimonial.getName())) {
            throw new ConflictException("There is already testimonial with name: "+testimonial.getName());
        }
        return testimonialRepository.save(testimonial).getId();
    }

    @Override
    @Transactional
    public void deleteTestimonial(long id) {
        testimonialRepository.findById(id).ifPresent(testimonialRepository::delete);
    }

    @Override
    public TestimonialList getList(PageRequest request) {
        Page<Testimonial> page = testimonialRepository.findAll(request);
        return new TestimonialList(page.getContent(), request, page.getTotalElements());
    }

    @Override
    public void updateTestimonialIfExist(long id, Testimonial testimonialRequest) {
        testimonialRepository.findById(id)
                .map((testimonialJpa)->{
                    testimonialJpa.setContent(testimonialRequest.getContent());
                    testimonialJpa.setImage(testimonialRequest.getImage());
                    testimonialJpa.setName(testimonialRequest.getImage());
                    return testimonialRepository.save(testimonialJpa);
                }).orElseThrow(()-> new NotFoundException(id));
    }

    private Boolean exist(String name){
    return testimonialRepository.existsByName(name);
    }



}

