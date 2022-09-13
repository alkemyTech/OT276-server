package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Contact;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ContactControllerMapper extends CommonMapper {

    Contact createContactRequestToContact(CreateContactRequest create);

    @Named("contactListToContactResponseList")
    List<ContactResponse> contactListToContactResponseList(List<Contact> contacts);
}
