package com.boufouss.registrationloginspringthymeleaf.model;

import com.boufouss.registrationloginspringthymeleaf.web.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;

    public User(String firstName, String lastName, String email,String password, Collection<Role> roles) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.password=password;
    }

    public User (UserRegistrationDto userRegistrationDto, String role) {
        this.firstName = userRegistrationDto.getFirstName();
        this.lastName = userRegistrationDto.getLastName();
        this.email = userRegistrationDto.getEmail();
        this.password=userRegistrationDto.getPassword();
        this.roles = roles;
    }

    public User(){

    }
}
