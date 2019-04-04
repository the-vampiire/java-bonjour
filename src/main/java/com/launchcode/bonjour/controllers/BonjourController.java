package com.launchcode.bonjour.controllers;

import com.launchcode.bonjour.utils.CountCookieUtil;
import com.launchcode.bonjour.models.Bonjour;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BonjourController {
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return Bonjour.getBonjourForm();
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String submitHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam String name,
        @RequestParam String language
    ) {
        String greeting = Bonjour.getGreeting(language);

        if (greeting == null) {
            return "Greeting not found";
        }

        // get new greeting count
        int greetingsCount = CountCookieUtil.getGreetingCount(request, name);

        // attach the greeting count cookie for the given User name
        CountCookieUtil.attachGreetingCountCookie(response, name, greetingsCount);

        return greeting + " " + name + " (you have been greeted: " + greetingsCount + " times)";
    }
}
