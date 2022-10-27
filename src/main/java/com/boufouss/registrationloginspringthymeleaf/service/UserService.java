package com.boufouss.registrationloginspringthymeleaf.service;

import com.boufouss.registrationloginspringthymeleaf.model.User;
import com.boufouss.registrationloginspringthymeleaf.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto);
}
