package com.imd.comasy.service;

import com.imd.comasy.dao.SuggestionDAO;
import com.imd.comasy.dto.SuggestionDTO;
import com.imd.comasy.model.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {

    @Autowired
    private SuggestionDAO suggestionDAO;

    public List<Suggestion> findAll(){
        return suggestionDAO.findAll();
    }

    public Suggestion findById(Long id){
        return suggestionDAO.findById(id);
    }

    public int save(SuggestionDTO suggestionDTO){
        return suggestionDAO.save(suggestionDTO);
    }

    public int update(SuggestionDTO suggestionDTO){
        return suggestionDAO.update(suggestionDTO);
    }

    public int delete(Long id){
        return suggestionDAO.delete(id);
    }
}
