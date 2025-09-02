package com.alchemist.yoru.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
*
* @author Atul Mundaware
* @since 17 04 2023
*/

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Where(clause = "isActive = true")
@Table(name = "user")
public class User extends BaseEntity implements Serializable {
   
	private static final long serialVersionUID = 7657451394244852266L;

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountNonExpired = user.isAccountNonExpired();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.roles = user.getRoles();
    }

    @Column(name = "username",unique = true,nullable = false)
    private String username;
    
    @Column(name = "password",nullable = false)
    private String password;
    
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    
    @Column(name = "enabled",nullable = false)
    private boolean enabled;
    
    @Column(name = "accountNonExpired",nullable = false)
    private boolean accountNonExpired;
    
    @Column(name = "credentialsNonExpired",nullable = false)
    private boolean credentialsNonExpired;
    
    @Column(name = "accountNonLocked",nullable = false)
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


//User RESET PASSWORD AND VERIFICATION

    private String confirmationToken;//this token is ofr the confirmation and registration of user
    private boolean isConfirmed;
    private String token;//this token is for the user email reset
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime tokenCreationDate;





    public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
        this.tokenCreationDate = tokenCreationDate;
    }
    public LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

}
