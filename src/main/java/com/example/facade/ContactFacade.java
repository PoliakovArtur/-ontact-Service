package com.example.facade;

import com.example.dto.ContactDto;
import com.example.exception.NotFoundException;
import com.example.mapper.ContactMapper;
import com.example.model.Contact;
import com.example.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactFacade {

    private final ContactMapper mapper;
    private final ContactService service;

    public List<ContactDto> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    public void save(ContactDto dto) {
        service.save(mapper.fromDto(dto));
    }

    public ContactDto findById(long id) {
        return mapper.toDto(service.findById(id));
    }

    public void updateById(ContactDto dto) {
        service.updateById(mapper.fromDto(dto));
    }

    public void deleteById(long id) {
        service.deleteById(id);
    }
}
