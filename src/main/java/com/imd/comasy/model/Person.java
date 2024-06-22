package com.imd.comasy.model;

import com.imd.comasy.utils.EnumRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person implements UserDetails {

    private String cpf;
    private String name;
    private Date birthday;
    private List<String> phoneNumber;
    private String cnh;
    private String photoUrl;
    private String username;
    private String password;
    private EnumRole role;

    public Person(Person person) {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == EnumRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_RESIDENT"),
                    new SimpleGrantedAuthority("ROLE_DOORMAN"));
        } else if(this.role == EnumRole.MANAGER) {
            return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_RESIDENT"));
        } else if (this.role == EnumRole.RESIDENT) {
            return List.of(new SimpleGrantedAuthority("ROLE_RESIDENT"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_DOORMAN"));
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
