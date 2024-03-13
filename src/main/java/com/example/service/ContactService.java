package com.example.service;

import com.example.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();
    void save(Contact contact);
    Contact findById(long id);
    void updateById(Contact contact);
    void deleteById(long id);
}
