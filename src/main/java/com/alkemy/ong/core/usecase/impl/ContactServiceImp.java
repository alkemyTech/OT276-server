package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.Contact;
import com.alkemy.ong.core.repository.ContactRepository;
import com.alkemy.ong.core.usecase.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactServiceImp implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public Long createEntity(Contact contact) {

        return contactRepository.save(contact).getId();
    }
}
