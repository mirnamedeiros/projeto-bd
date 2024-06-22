package com.imd.comasy.service;

import com.imd.comasy.dao.PersonDAO;
import com.imd.comasy.dto.AuthenticationDTO;
import com.imd.comasy.dto.PersonDTO;
import com.imd.comasy.exceptions.AuthenticationInvalidException;
import com.imd.comasy.exceptions.EntityAlreadyExistsException;
import com.imd.comasy.exceptions.InvalidFieldException;
import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private PersonService personService;

    public String authenticate(AuthenticationDTO auth) {
        validateLoginFields(auth);
        String username = auth.getUsername();
        String password = auth.getPassword();

        Person person = personService.findByUsername(username);

        if (person == null) {
            throw new AuthenticationInvalidException();
        }

        if (password.equals(person.getPassword())) {
            // Autenticação bem-sucedida
            return "Authentication successful.";
        } else {
            // Senha incorreta
            throw new AuthenticationInvalidException();
        }
    }

    public void register(PersonDTO data) throws EntityAlreadyExistsException {
        if (personService.findByUsername(data.getUsername()) != null) {
            throw new EntityAlreadyExistsException();
        }

        EnumRole role = EnumRole.valueOf(data.getRole().toUpperCase());
        Person newUser = new Person(
                data.getCpf(),
                data.getName(),
                data.getBirthday(),
                data.getPhoneNumber(),
                data.getCnh(),
                data.getPhotoUrl(),
                data.getUsername(),
                data.getPassword(),
                role
        );

        personService.addPerson(newUser);
    }

    private void validateLoginFields(AuthenticationDTO auth) {
        if (auth.getUsername() == null || auth.getUsername().isEmpty()) {
            throw new InvalidFieldException("Username field is required.");
        }
        if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new InvalidFieldException("Password field is required.");
        }
    }
}
