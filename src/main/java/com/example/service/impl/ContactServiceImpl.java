package com.example.service.impl;

import com.example.exception.NotFoundException;
import com.example.model.Contact;
import com.example.repository.ContactRepository;
import com.example.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;

    @Override
    public List<Contact> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Contact contact) {
        if(contact.getId() != null && repository.findById(contact.getId()).isPresent()) {
            repository.updateById(contact);
        } else {
            repository.save(contact);
        }
    }

    @Override
    public Contact findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Контакт не найден"));
    }

    @Override
    public void deleteById(long id) {
        int updateRows = repository.deleteById(id);
        if(updateRows == 0) throw new NotFoundException("Контакт не найден");
    }
}
