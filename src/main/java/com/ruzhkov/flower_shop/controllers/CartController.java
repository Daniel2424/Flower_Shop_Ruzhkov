package com.ruzhkov.flower_shop.controllers;


import com.ruzhkov.flower_shop.entity.Flower;
import com.ruzhkov.flower_shop.entity.Person;
import com.ruzhkov.flower_shop.services.FlowerService;
import com.ruzhkov.flower_shop.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private PersonDetailsService userService;
    @Autowired
    FlowerService flowerService;

    public CartController() {
    }

    @GetMapping({"/cart"})
    public String cartProduct(Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();


        Person user = (Person) userService.getPerson(authentication.getName());
        int total = this.findSum(user);
        model.addAttribute("user", user);
        model.addAttribute("total", total);
        model.addAttribute("person", authentication.getName());
        return "/carts/cart";
    }

    private int findSum(Person user) {
        List<Flower> flowerList = user.getFlowerList();
        int sum = 0;

        Flower p;
        for(Iterator var4 = flowerList.iterator(); var4.hasNext(); sum += p.getPrice()) {
            p = (Flower)var4.next();
        }

        return sum;
    }

    @GetMapping({"/cart/add/{id}"})
    public String addToCart(@PathVariable("id") int flowerId) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        Person user = userService.getPerson(authentication.getName());
        Flower flower = flowerService.getFlower(flowerId);

        List<Person> userList = new ArrayList();
        userList.add(user);
        flower.setPersonList(userList);

        userService.update(user, flower);
        flowerService.addProduct(flower);

        int total = this.findSum(user);

        return "redirect:/cart";
    }
    @GetMapping("/cart/delete/{id}")
    public String delete(@PathVariable("id") int id){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        Person user = userService.getPerson(authentication.getName());
        Flower flower = flowerService.getFlower(id);



        userService.delete(user, flower);
        flowerService.delete(flower,user);

        return "redirect:/cart";
    }

}
