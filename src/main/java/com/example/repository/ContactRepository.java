package com.example.repository;

import com.example.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    int updateById(Contact contact);
    int deleteById(Long id);
    void save(Contact contact);
}
