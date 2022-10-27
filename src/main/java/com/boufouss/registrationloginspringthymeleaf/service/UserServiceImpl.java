package com.boufouss.registrationloginspringthymeleaf.service;

import com.boufouss.registrationloginspringthymeleaf.model.Role;
import com.boufouss.registrationloginspringthymeleaf.model.User;
import com.boufouss.registrationloginspringthymeleaf.repository.UserRepository;
import com.boufouss.registrationloginspringthymeleaf.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Collections.singletonList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        System.out.println("user = " + user);

        if (passwordEncoder.matches("password", user.getPassword())) {
            //BCrypt.checkpw("password",user.getPassword());
            System.out.println("password match");
        } else System.out.println("password wrong");

        if (user == null) {
            System.out.println("user IS NULL NO WAY USERSERVICEIMPL UserDetails loadUserByUsername");
            throw new UsernameNotFoundException("Email or password incorrect");
        }

        //(Role[]) user.getRoles().toArray()

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuth(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuth(Collection<Role> roles) {
     /*Role[] roles   Collection<GrantedAuthority> cg = new ArrayList<>();

        SimpleGrantedAuthority sga = new SimpleGrantedAuthority(roles[0].getName());
        cg.add(sga);

        return cg;*/

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
