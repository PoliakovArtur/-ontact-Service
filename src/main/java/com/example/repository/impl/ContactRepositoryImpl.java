package com.example.repository.impl;

import com.example.model.Contact;
import com.example.repository.ContactRepository;
import com.example.repository.utils.ContactRowMapper;
import com.example.repository.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactRowMapper rowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final QueryUtils queryBuilder;

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
    public int updateById(Contact contact) {
        return jdbcTemplate.update(
                queryBuilder.createUpdateQueryById("contacts", contact),
                queryBuilder.gettersFromNotNullFields(contact).stream().map(Supplier::get).toArray());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", id);
    }

    @Override
    public void save(Contact contact) {
        jdbcTemplate.update("INSERT INTO contacts (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)",
                contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhoneNumber());
    }
}
