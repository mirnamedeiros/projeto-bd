package com.imd.comasy.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Suggestion {
    private Long id;
    private String type;
    private String message;
    private Integer qtdVotos;
    private Date dataProposta;
    private String residentId;
    private Boolean active;
}
