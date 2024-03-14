package com.example.repository.impl;

import com.example.model.Contact;
import com.example.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactRowMapper rowMapper;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        return jdbcTemplate.query("SELECT * FROM contacts", rowMapper);
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT * FROM contacts WHERE id = ?",
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(rowMapper, 1))));
    }

    @Override
    public int updateById(Contact c) {
        return jdbcTemplate.update(
                "UPDATE contacts SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id = ?",
                c.getFirstName(), c.getLastName(), c.getEmail(), c.getPhoneNumber(), c.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", id);
    }

    @Override
    public void save(Contact c) {
        jdbcTemplate.update("INSERT INTO contacts (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)",
                c.getFirstName(), c.getLastName(), c.getEmail(), c.getPhoneNumber());
    }
}
