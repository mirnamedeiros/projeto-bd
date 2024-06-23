package com.imd.comasy.controller;

import com.imd.comasy.dto.AuthenticationDTO;
import com.imd.comasy.dto.DoormanDTO;
import com.imd.comasy.dto.PersonDTO;
import com.imd.comasy.dto.ResidentDTO;
import com.imd.comasy.exceptions.AuthenticationInvalidException;
import com.imd.comasy.exceptions.EntityAlreadyExistsException;
import com.imd.comasy.model.Person;
import com.imd.comasy.service.AuthenticationService;
import com.imd.comasy.service.DoormanService;
import com.imd.comasy.service.PersonService;
import com.imd.comasy.service.ResidentService;
import com.imd.comasy.utils.EnumRole;
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
    private DoormanService doormanService;

    @Autowired
    private ResidentService residentService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthenticationDTO auth, HttpServletResponse response) {
        try {
            String result = authenticationService.authenticate(auth);
            // Autenticação bem-sucedida
            return ResponseEntity.ok().body(result);
        } catch (AuthenticationInvalidException e) {
            // Usuário não encontrado ou senha incorreta
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PersonDTO person) {
        try {
            authenticationService.register(person);
            if (EnumRole.DOORMAN.equals(person.getRole())) {
                doormanService.save(new DoormanDTO(person.getCpf()));
            } else if (EnumRole.RESIDENT.equals(person.getRole())) {
                ResidentDTO residentDTO = new ResidentDTO(person.getCpf(), person.getHolderCpf(), person.getApartmentNumber(), person.getBlockNumber());
                residentService.addResident(residentDTO);
            }
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

