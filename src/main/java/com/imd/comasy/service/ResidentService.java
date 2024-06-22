package com.imd.comasy.service;

import com.imd.comasy.dao.ResidentDAO;
import com.imd.comasy.dto.ResidentDTO;
import com.imd.comasy.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private ResidentDAO residentDAO;

    public List<Resident> getAllResidents() {
        return residentDAO.findAll();
    }

    public Resident getResidentByPersonCpf(String personCpf) {
        return residentDAO.findById(personCpf);
    }

    public void addResident(ResidentDTO resident) {
        residentDAO.save(resident);
    }

    public void updateResident(ResidentDTO resident) {
        residentDAO.update(resident);
    }

    public void deleteResident(String personCpf) {
        residentDAO.delete(personCpf);
    }
}

