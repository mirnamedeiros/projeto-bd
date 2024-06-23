package com.imd.comasy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    private Integer id;
    private String status;
    private String residentCpf;
    private Date arrivalDate;
    private String doormanCpf;
}
