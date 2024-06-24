package com.imd.comasy.service;

import com.imd.comasy.dao.CondominiumDAO;
import com.imd.comasy.model.Condominium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondominiumService {

    @Autowired
    private CondominiumDAO condominiumDAO;

    public List<Condominium> getAllCondominiums() {
        return condominiumDAO.findAll();
    }

    public Condominium getCondominiumById(int id) {
        return condominiumDAO.findById(id);
    }

    public void createCondominium(Condominium condominium) {
        condominiumDAO.save(condominium);
    }

    public Condominium updateCondominium(int id, Condominium condominium) {
        Condominium existingCondominium = getCondominiumById(id);
        if (existingCondominium != null) {
            condominium.setId(id);
            condominiumDAO.update(id, condominium);
            return condominium;
        } else {
            return null;
        }
    }

    public void deleteCondominium(int id) {
        condominiumDAO.delete(id);
    }
}
