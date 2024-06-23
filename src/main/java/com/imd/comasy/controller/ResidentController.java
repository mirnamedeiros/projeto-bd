package com.imd.comasy.controller;

import com.imd.comasy.dto.ResidentDTO;
import com.imd.comasy.model.Resident;
import com.imd.comasy.service.PersonService;
import com.imd.comasy.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Resident> getAllResidents() {
        return residentService.getAllResidents();
    }

    @GetMapping("/form")
    public ModelAndView residentForm() {
        ModelAndView model = new ModelAndView("resident/form");
        model.addObject("persons", personService.getAllPersons());
        return model;
    }

    @GetMapping("/{personCpf}")
    public Resident getResidentByPersonCpf(@PathVariable String personCpf) {
        return residentService.getResidentByPersonCpf(personCpf);
    }

    @PostMapping
    public ResponseEntity<?> addResident(@RequestBody ResidentDTO resident) {
        try {
            residentService.addResident(resident);
            return new ResponseEntity<>("Resident added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{personCpf}")
    public ResponseEntity<String> updateResident(@PathVariable String personCpf, @RequestBody ResidentDTO resident) {
        resident.setPersonCpf(personCpf);
        residentService.updateResident(resident);
        return new ResponseEntity<>("Resident updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{personCpf}")
    public ResponseEntity<String> deleteResident(@PathVariable String personCpf) {
        residentService.deleteResident(personCpf);
        return new ResponseEntity<>("Resident deleted successfully", HttpStatus.OK);
    }
}

