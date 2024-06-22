package com.imd.comasy.dao;

import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, new PersonRowMapper());
    }

    public Person findById(String cpf) {
        String sql = "SELECT * FROM person WHERE cpf = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cpf}, new PersonRowMapper());
    }

    public Person findByUsername(String username) {
        String sql = "SELECT * FROM person WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new PersonRowMapper());
    }

    public int save(Person person) {
        String sql = "INSERT INTO person (cpf, name, birthday, cnh) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, person.getCpf(), person.getName(), person.getBirthday(), person.getCnh());
    }

    public int update(Person person) {
        String sql = "UPDATE person SET name = ?, birthday = ?, cnh = ? WHERE cpf = ?";
        return jdbcTemplate.update(sql, person.getName(), person.getBirthday(), person.getCnh(), person.getCpf());
    }

    public int delete(String cpf) {
        String sql = "DELETE FROM person WHERE cpf = ?";
        return jdbcTemplate.update(sql, cpf);
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
            return person;
        }
    }
}

