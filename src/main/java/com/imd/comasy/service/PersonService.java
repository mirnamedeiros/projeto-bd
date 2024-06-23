package com.imd.comasy.service;

import com.imd.comasy.dao.PersonDAO;
import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public List<Person> getAllPersons() {
        return personDAO.findAll();
    }

    public List<Person> getAllDoormen() {
        return personDAO.findAllByRole(EnumRole.DOORMAN);
    }

    public List<Person> getAllResidents() {
        return personDAO.findAllByRole(EnumRole.RESIDENT);
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

    public Person findByUsername(String username) {
        return personDAO.findByUsername(username);
    }
}
