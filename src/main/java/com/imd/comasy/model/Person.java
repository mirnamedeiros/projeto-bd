package com.imd.comasy.model;

import com.imd.comasy.utils.EnumRole;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person{

    private String cpf;
    private String name;
    private Date birthday;
    private List<String> phoneNumber;
    private String cnh;
    private String photoUrl;
    private String username;
    private String password;
    private EnumRole role;

}
