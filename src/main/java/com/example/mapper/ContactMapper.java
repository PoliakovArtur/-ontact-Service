package com.example.mapper;

import com.example.dto.ContactDto;
import com.example.model.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact fromDto(ContactDto contactDto);
    ContactDto toDto(Contact contact);
    List<ContactDto> toDtoList(List<Contact> contacts);
}
