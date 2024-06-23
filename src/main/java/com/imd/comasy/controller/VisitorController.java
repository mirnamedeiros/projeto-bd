package com.imd.comasy.controller;

import com.imd.comasy.dto.VisitorDTO;
import com.imd.comasy.service.VisitorService;
import com.imd.comasy.utils.EnumVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping
    public ResponseEntity<List<VisitorDTO>> getAllVisitors() {
        List<VisitorDTO> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/form")
    public ModelAndView visitorForm() {
        ModelAndView modelAndView = new ModelAndView("visitor/form");
        modelAndView.addObject("visitorTypes", Arrays.asList(EnumVisitor.values()));
        return modelAndView;
    }

    @GetMapping("/{code}")
    public ResponseEntity<VisitorDTO> getVisitorByCode(@PathVariable Long code) {
        Optional<VisitorDTO> visitor = visitorService.getVisitorByCode(code);
        return visitor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VisitorDTO> createVisitor(@RequestBody VisitorDTO visitor) {
        VisitorDTO createdVisitor = visitorService.createVisitor(visitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitor);
    }

    @PutMapping("/{code}")
    public ResponseEntity<VisitorDTO> updateVisitor(@PathVariable Long code, @RequestBody VisitorDTO visitorDetails) {
        VisitorDTO updatedVisitor = visitorService.updateVisitor(code, visitorDetails);
        return ResponseEntity.ok(updatedVisitor);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<VisitorDTO> deleteVisitor(@PathVariable Long code) {
        visitorService.deleteVisitor(code);
        return ResponseEntity.noContent().build();
    }
}
