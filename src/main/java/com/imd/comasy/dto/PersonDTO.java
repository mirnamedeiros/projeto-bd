package com.imd.comasy.dto;

import com.imd.comasy.utils.EnumRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonDTO {

    String cpf;
    String name;
    Date birthday;
    List<String> phoneNumber;
    String cnh;
    String photoUrl;
    String username;
    String password;
    EnumRole role;
}
