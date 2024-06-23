package com.imd.comasy.service;

import com.imd.comasy.dao.DeliveryDAO;
import com.imd.comasy.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryDAO deliveryDAO;

    public List<Delivery> getAllDeliveries() {
        return deliveryDAO.findAll();
    }

    public Delivery getDeliveryById(Integer id) {
        return deliveryDAO.findById(id);
    }

    public void addDelivery(Delivery delivery) {
        deliveryDAO.save(delivery);
    }

    public void updateDelivery(Delivery delivery) {
        deliveryDAO.update(delivery);
    }

    public void deleteDelivery(Integer id) {
        deliveryDAO.delete(id);
    }
}
