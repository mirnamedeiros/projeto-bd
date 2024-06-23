package com.imd.comasy.service;

import com.imd.comasy.dao.DoormanDAO;
import com.imd.comasy.dto.DoormanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoormanService {

    @Autowired
    private DoormanDAO doormanDAO;

    public void save(DoormanDTO data) {

        doormanDAO.save(data);
    }
}