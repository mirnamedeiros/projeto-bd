package com.imd.comasy.service;

import com.imd.comasy.dao.VisitorDAO;
import com.imd.comasy.dto.VisitorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    private final VisitorDAO visitorDAO;

    @Autowired
    public VisitorService(VisitorDAO visitorDAO) {
        this.visitorDAO = visitorDAO;
    }

    public List<VisitorDTO> getAllVisitors() {
        return visitorDAO.findAll();
    }

    public Optional<VisitorDTO> getVisitorByCode(Long code) {
        return Optional.ofNullable(visitorDAO.findByCode(code));
    }

    public VisitorDTO createVisitor(VisitorDTO visitor) {
        visitorDAO.save(visitor);
        return visitor;
    }

    public VisitorDTO updateVisitor(Long code, VisitorDTO visitorDetails) {
        visitorDAO.update(code, visitorDetails);
        return visitorDetails;
    }

    public void deleteVisitor(Long code) {
        visitorDAO.delete(code);
    }
}
