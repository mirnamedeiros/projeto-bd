package com.imd.comasy.controller;

import com.imd.comasy.service.PersonService;
import com.imd.comasy.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(name = "/")
public class IndexController {

    @Autowired
    PersonService personService;

    public IndexController() {}

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("roles", EnumRole.values());
        return new ModelAndView("register");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/menu")
    public ModelAndView visitMenu() {
        return new ModelAndView("menu");
    }
}
