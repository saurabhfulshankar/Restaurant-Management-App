package com.alchemist.yoru.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserDto{

    private long id;

    private String username;

    private String password;

    private String email;

    private boolean enabled = true;

    private boolean accountNonExpired = true;

    private boolean credentialsNonExpired= true;

    private boolean accountNonLocked= true;

}
