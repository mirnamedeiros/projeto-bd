package com.imd.comasy.controller;

import com.imd.comasy.model.Visit;
import com.imd.comasy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public ModelAndView visitList() {
        ModelAndView modelAndView = new ModelAndView("visit/list");
        modelAndView.addObject("visits", visitService.getAllVisits());
        return modelAndView;
    }

    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits() {
        List<Visit> visits = visitService.getAllVisits();
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/form")
    public ModelAndView visitForm() {
        ModelAndView modelAndView = new ModelAndView("visit/form");
        modelAndView.addObject("doormen", personService.getAllDoormen());
        modelAndView.addObject("visitors", visitorService.getAllVisitors());
        modelAndView.addObject("residents", personService.getAllResidents());
//        modelAndView.addObject("statuses", EnumStatusVisitor.values());
        return modelAndView;
    }

    @GetMapping("/{doormanCpf}/{visitorCode}/{residentCpf}")
    public ResponseEntity<Visit> getVisitByIds(@PathVariable String doormanCpf,
                                               @PathVariable int visitorCode,
                                               @PathVariable String residentCpf) {
        Visit visit = visitService.getVisitByIds(doormanCpf, visitorCode, residentCpf);
        return visit != null ? ResponseEntity.ok(visit) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        visitService.createVisit(visit);
        return ResponseEntity.ok(visit);
    }

    @PutMapping("/{doormanCpf}/{visitorCode}/{residentCpf}")
    public ResponseEntity<Visit> updateVisit(@PathVariable String doormanCpf,
                                             @PathVariable int visitorCode,
                                             @PathVariable String residentCpf,
                                             @RequestBody Visit visitDetails) {
        Visit updatedVisit = visitService.updateVisit(doormanCpf, visitorCode, residentCpf, visitDetails);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{doormanCpf}/{visitorCode}/{residentCpf}")
    public ResponseEntity<Void> deleteVisit(@PathVariable String doormanCpf,
                                            @PathVariable int visitorCode,
                                            @PathVariable String residentCpf) {
        visitService.deleteVisit(doormanCpf, visitorCode, residentCpf);
        return ResponseEntity.noContent().build();
    }
}

