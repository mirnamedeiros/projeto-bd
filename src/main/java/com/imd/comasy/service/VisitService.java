package com.imd.comasy.service;

import com.imd.comasy.dao.VisitDAO;
import com.imd.comasy.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitDAO visitDAO;

    public List<Visit> getAllVisits() {
        return visitDAO.findAll();
    }

    public Visit getVisitByIds(String doormanCpf, int visitorCode, String residentCpf) {
        return visitDAO.findByIds(doormanCpf, visitorCode, residentCpf);
    }

    public void createVisit(Visit visit) {
        visitDAO.save(visit);
    }

    public Visit updateVisit(String doormanCpf, int visitorCode, String residentCpf, Visit visitDetails) {
        visitDAO.update(doormanCpf, visitorCode, residentCpf, visitDetails);
        return visitDetails;
    }

    public void deleteVisit(String doormanCpf, int visitorCode, String residentCpf) {
        visitDAO.delete(doormanCpf, visitorCode, residentCpf);
    }
}

