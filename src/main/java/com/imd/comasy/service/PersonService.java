package com.imd.comasy.service;

import com.imd.comasy.dao.PersonDAO;
import com.imd.comasy.dto.PersonDTO;
import com.imd.comasy.exceptions.AuthenticationInvalidException;
import com.imd.comasy.exceptions.EntityAlreadyExistsException;
import com.imd.comasy.exceptions.InvalidFieldException;
import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService implements UserDetailsService {

    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public List<Person> getAllPersons() {
        return personDAO.findAll();
    }

    public Person getPersonByCpf(String cpf) {
        return personDAO.findById(cpf);
    }

    public void addPerson(Person person) {
        personDAO.save(person);
    }

    public void updatePerson(Person person) {
        personDAO.update(person);
    }

    public void deletePerson(String cpf) {
        personDAO.delete(cpf);
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDAO.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new Person(person);
    }

    public Person findByUsername(String username) {
        return personDAO.findByUsername(username);
    }
}
