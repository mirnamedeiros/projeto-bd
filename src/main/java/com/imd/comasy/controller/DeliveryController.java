package com.imd.comasy.controller;

import com.imd.comasy.model.Delivery;
import com.imd.comasy.service.DeliveryService;
import com.imd.comasy.service.PersonService;
import com.imd.comasy.service.ResidentService;
import com.imd.comasy.utils.EnumDeliveryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable Integer id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping("/form")
    public ModelAndView deliveryForm() {
        ModelAndView model = new ModelAndView("delivery/form");
        model.addObject("residents", personService.getAllResidents());
        model.addObject("doormen", personService.getAllDoormen());
        model.addObject("statuses", EnumDeliveryStatus.values());
        return model;
    }

    @PostMapping
    public ResponseEntity<String> addDelivery(@RequestBody Delivery delivery) {
        try {
            deliveryService.addDelivery(delivery);
            return new ResponseEntity<>("Delivery added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDelivery(@PathVariable Integer id, @RequestBody Delivery delivery) {
        delivery.setId(id);
        deliveryService.updateDelivery(delivery);
        return new ResponseEntity<>("Delivery updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Integer id) {
        deliveryService.deleteDelivery(id);
        return new ResponseEntity<>("Delivery deleted successfully", HttpStatus.OK);
    }
}

