package com.imd.comasy.controller;

import com.imd.comasy.dto.SuggestionDTO;
import com.imd.comasy.model.Suggestion;
import com.imd.comasy.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping
    public ResponseEntity<List<Suggestion>> findAll() {
        List<Suggestion> suggestions = suggestionService.findAll();
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> findById(@PathVariable Long id){
        Suggestion suggestion = suggestionService.findById(id);
        return ResponseEntity.ok(suggestion);
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody SuggestionDTO suggestionDTO){
        int result = suggestionService.save(suggestionDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> update(@PathVariable Long id, @RequestBody SuggestionDTO suggestionDTO){
        suggestionDTO.setId(id);
        int result = suggestionService.update(suggestionDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id){
        int result = suggestionService.delete(id);
        return ResponseEntity.ok(result);
    }
}
