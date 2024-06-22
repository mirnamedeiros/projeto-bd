package com.imd.comasy.dao;

import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@Repository
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> persons = jdbcTemplate.query(sql, new PersonRowMapper());
        persons.forEach(this::loadPhoneNumbers);
        return persons;
    }

    public Person findById(String cpf) {
        String sql = "SELECT * FROM person WHERE cpf = ?";
        Person person = jdbcTemplate.queryForObject(sql, new Object[]{cpf}, new PersonRowMapper());
        loadPhoneNumbers(person);
        return person;
    }

    public Person findByUsername(String username) {
        String sql = "SELECT * FROM person WHERE username = ?";
        List<Person> persons = jdbcTemplate.query(sql, new Object[]{username}, new PersonRowMapper());
        if (persons.isEmpty()) {
            return null;
        }
        Person person = persons.get(0);
        loadPhoneNumbers(person);
        return person;
    }

    public int save(Person person) {
        String sql = "INSERT INTO person (cpf, name, birthday, cnh, photo_url, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, person.getCpf(), person.getName(), person.getBirthday(), person.getCnh(), person.getPhotoUrl(), person.getUsername(), person.getPassword(), person.getRole().name());
        savePhoneNumbers(person);
        return result;
    }

    public int update(Person person) {
        String sql = "UPDATE person SET name = ?, birthday = ?, cnh = ?, photo_url = ?, username = ?, password = ?, role = ? WHERE cpf = ?";
        int result = jdbcTemplate.update(sql, person.getName(), person.getBirthday(), person.getCnh(), person.getPhotoUrl(), person.getUsername(), person.getPassword(), person.getRole().name(), person.getCpf());
        updatePhoneNumbers(person);
        return result;
    }

    public int delete(String cpf) {
        String sql = "DELETE FROM person WHERE cpf = ?";
        int result = jdbcTemplate.update(sql, cpf);
        deletePhoneNumbers(cpf);
        return result;
    }

    private void savePhoneNumbers(Person person) {
        String sql = "INSERT INTO phone_numbers (person_cpf, phone_number) VALUES (?, ?)";
        for (String phoneNumber : person.getPhoneNumber()) {
            jdbcTemplate.update(sql, person.getCpf(), phoneNumber);
        }
    }

    private void updatePhoneNumbers(Person person) {
        deletePhoneNumbers(person.getCpf());
        savePhoneNumbers(person);
    }

    private void deletePhoneNumbers(String cpf) {
        String sql = "DELETE FROM phone_numbers WHERE person_cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }

    private void loadPhoneNumbers(Person person) {
        String sql = "SELECT phone_number FROM phone_numbers WHERE person_cpf = ?";
        List<String> phoneNumbers = jdbcTemplate.query(sql, new Object[]{person.getCpf()}, (rs, rowNum) -> rs.getString("phone_number"));
        person.setPhoneNumber(phoneNumbers);
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setCpf(rs.getString("cpf"));
            person.setName(rs.getString("name"));
            person.setBirthday(rs.getDate("birthday"));
            person.setCnh(rs.getString("cnh"));
            person.setPhotoUrl(rs.getString("photo_url"));
            person.setUsername(rs.getString("username"));
            person.setPassword(rs.getString("password"));
            person.setRole(EnumRole.valueOf(rs.getString("role")));
            person.setPhoneNumber(new ArrayList<>());
            return person;
        }
    }
}
