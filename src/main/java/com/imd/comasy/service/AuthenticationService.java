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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PersonService personService;

    public String authenticate(PersonDTO data){
        validateLoginFields(data);

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            return tokenService.generateToken((Person) auth.getPrincipal());
        } catch (Exception e) {
            throw new AuthenticationInvalidException();
        }
    }

    public void register(PersonDTO data) throws EntityAlreadyExistsException {
        if (personService.findByUsername(data.getUsername()) != null) {
            throw new EntityAlreadyExistsException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        EnumRole role = EnumRole.valueOf(data.getRole().toUpperCase());
        Person newUser = new Person(
                data.getCpf(),
                data.getName(),
                data.getBirthday(),
                data.getPhoneNumber(),
                data.getCnh(),
                data.getPhotoUrl(),
                data.getUsername(),
                encryptedPassword,
                role
        );

        personService.addPerson(newUser);
    }

    private void validateLoginFields(PersonDTO data) {
        if (data.getUsername() == null || data.getUsername().isEmpty()) {
            throw new InvalidFieldException("Username field is required.");
        }
        if (data.getPassword() == null || data.getPassword().isEmpty()) {
            throw new InvalidFieldException("Password field is required.");
        }
    }
}
