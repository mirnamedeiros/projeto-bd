package com.imd.comasy.controller;

import com.imd.comasy.model.Condominium;
import com.imd.comasy.service.CondominiumService;
import com.imd.comasy.utils.EnumVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/condominium")
public class CondominiumController {

    @Autowired
    private CondominiumService condominiumService;

    @GetMapping
    public ResponseEntity<List<Condominium>> getAllCondominiums() {
        List<Condominium> condominiums = condominiumService.getAllCondominiums();
        return ResponseEntity.ok(condominiums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Condominium> getCondominiumById(@PathVariable int id) {
        Condominium condominium = condominiumService.getCondominiumById(id);
        if (condominium != null) {
            return ResponseEntity.ok(condominium);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/form")
    public ModelAndView condominiumForm() {
        return new ModelAndView("condominium/form");
    }

    @PostMapping
    public ResponseEntity<Condominium> createCondominium(@RequestBody Condominium condominium) {
        condominiumService.createCondominium(condominium);
        return ResponseEntity.status(HttpStatus.CREATED).body(condominium);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Condominium> updateCondominium(@PathVariable int id, @RequestBody Condominium condominium) {
        Condominium updatedCondominium = condominiumService.updateCondominium(id, condominium);
        if (updatedCondominium != null) {
            return ResponseEntity.ok(updatedCondominium);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondominium(@PathVariable int id) {
        condominiumService.deleteCondominium(id);
        return ResponseEntity.noContent().build();
    }
}
