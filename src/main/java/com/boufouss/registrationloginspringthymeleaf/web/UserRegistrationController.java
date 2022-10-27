package com.boufouss.registrationloginspringthymeleaf.web;

import com.boufouss.registrationloginspringthymeleaf.service.UserService;
import com.boufouss.registrationloginspringthymeleaf.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    public UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    /**
     * thymeleaf binds this returned object to form registration view
     *
     * this methodology also works
     *  @GetMapping
     *     public String showRegistrationForm(Model model){
     *         model.addAttribute("user",new UserRegistrationDto());
     *         return "registrationView";
     *     }
     * @return UserRegistrationDto
     */

    @GetMapping
    public String showRegistrationForm(){
        return "registrationView";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {

        userService.save(userRegistrationDto);

        return "redirect:/registration?success";
    }

}
