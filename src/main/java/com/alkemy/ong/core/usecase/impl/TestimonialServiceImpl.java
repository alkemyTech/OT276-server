package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.ConflictException;
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


    private final TestimonialRepository repository;


    @Override
    @Transactional
    public Long createNewTestimonial(Testimonial testimonial) {
        if (exist(testimonial.getName())) {

            throw new ConflictException("There is already testimonial with name: " + testimonial.getName());
        }

        return repository.save(testimonial).getId();
    }

    @Override
    @Transactional
    public void deleteTestimonial(long id) {

        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    @Transactional
    public TestimonialList getList(PageRequest request) {
        Page<Testimonial> page = repository.findAll(request);
        return new TestimonialList(page.getContent(), request, page.getTotalElements());
    }

    private Boolean exist(String name) {
        return repository.existsByName(name);
    }


}




