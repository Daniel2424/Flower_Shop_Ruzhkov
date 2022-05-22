package com.ruzhkov.flower_shop.controllers;

import com.ruzhkov.flower_shop.entity.Flower;
import com.ruzhkov.flower_shop.entity.Person;
import com.ruzhkov.flower_shop.services.FlowerService;
import com.ruzhkov.flower_shop.services.PersonDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
public class FlowerController {
    private final PersonDetailsService personDetailsService;
    private final FlowerService flowerService;

    public FlowerController(PersonDetailsService personDetailsService, FlowerService flowerService) {
        this.personDetailsService = personDetailsService;
        this.flowerService = flowerService;
    }

    @GetMapping("/")
    public String showFirstPage(Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        model.addAttribute("person", authentication.getName());
        return "/index";
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Person> allPerson = personDetailsService.getAllPerson();
        model.addAttribute("allPerson", allPerson);
        return "/admin";
    }

    @GetMapping("/flower/{id}")
    public String flowerId(@PathVariable("id") int id, Model model) {
        Flower flower = flowerService.getFlower(id);
        model.addAttribute("flower", flower);
        return "/flower";
    }

    @GetMapping("/test")
    public String index2(Model model) {
        Iterable<Flower> allFlowers = flowerService.getAll();
        List<Flower> products = new ArrayList<>((Collection) allFlowers);

        model.addAttribute("allFlowers", allFlowers);
        return "/index2";
    }
}
