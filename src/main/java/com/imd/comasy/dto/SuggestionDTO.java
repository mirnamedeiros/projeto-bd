package com.imd.comasy.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class SuggestionDTO {
    private Long id;
    private String type;
    private String message;
    private Integer qtdVotos;
    private Date dataProposta;
    private String residentId;
    private Boolean active;
}
