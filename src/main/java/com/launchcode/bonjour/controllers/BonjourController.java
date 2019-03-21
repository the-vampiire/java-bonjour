package com.launchcode.bonjour.controllers;


import models.Bonjour;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BonjourController {
    private static int greetingsCount = 0;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return Bonjour.getBonjourForm();
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String submitHandler(@RequestParam String name, @RequestParam String language) {
        String greeting = Bonjour.getGreeting(language);

        if (greeting == null) {
            return "Greeting not found";
        }

        ++greetingsCount;
        return greeting + " " + name + " (greeted " + greetingsCount + " times)";
    }
}
