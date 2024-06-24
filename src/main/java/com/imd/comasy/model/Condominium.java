package com.imd.comasy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Condominium {

    private Integer id;
    private String name;
    private String cnpj;
    private String cep;
    private String neighborhood;
    private String street;
    private String city;
    private String state;
    private String phone;
}
