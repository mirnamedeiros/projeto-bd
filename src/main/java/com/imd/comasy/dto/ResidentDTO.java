package com.imd.comasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDTO {

    private String personCpf;
    private String holderCpf;
    private Integer apartmentNumber;
    private Integer blockNumber;
}
