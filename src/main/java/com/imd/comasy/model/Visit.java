package com.imd.comasy.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Visit {
    private String doormanCpf;
    private int visitorCode;
    private String residentCpf;
    private String status;
    private Timestamp arrivalDate;
    private Timestamp departureDate;
}