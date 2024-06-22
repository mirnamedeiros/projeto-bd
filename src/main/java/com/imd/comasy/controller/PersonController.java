package com.imd.comasy.controller;

import com.imd.comasy.dto.PersonDTO;
import com.imd.comasy.exceptions.AuthenticationInvalidException;
import com.imd.comasy.exceptions.EntityAlreadyExistsException;
import com.imd.comasy.exceptions.InvalidFieldException;
import com.imd.comasy.model.Person;
import com.imd.comasy.service.AuthenticationService;
import com.imd.comasy.service.PersonService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated PersonDTO data, HttpServletResponse response) {
        try {
            String token = authenticationService.authenticate(data);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 1 dia de validade
            response.addCookie(cookie);
        } catch (InvalidFieldException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (AuthenticationInvalidException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "An internal server error occurred."));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Person data) {

        System.out.println("vixe");

        try {
            authenticationService.register(data);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{cpf}")
    public Person getPersonByCpf(@PathVariable String cpf) {
        return personService.getPersonByCpf(cpf);
    }

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return new ResponseEntity<>("Person added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<String> updatePerson(@PathVariable String cpf, @RequestBody Person person) {
        person.setCpf(cpf);
        personService.updatePerson(person);
        return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletePerson(@PathVariable String cpf) {
        personService.deletePerson(cpf);
        return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
    }
}

