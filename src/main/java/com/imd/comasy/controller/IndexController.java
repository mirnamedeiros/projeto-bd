package com.imd.comasy.controller;

import com.imd.comasy.dto.PersonDTO;
import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(name = "/")
public class IndexController {

    public IndexController() {}

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        model.addAttribute("person", new PersonDTO());
        model.addAttribute("roles", EnumRole.values());
        return new ModelAndView("register");
    }
}
