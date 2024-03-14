package com.example.service;

import com.example.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();
    void save(Contact contact);
    Contact findById(long id);
    void deleteById(long id);
}
