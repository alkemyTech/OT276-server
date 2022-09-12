package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Contact;

import java.util.List;

public interface ContactService {

    Long createEntity(Contact contact);

    List<Contact> getContacts();
}
