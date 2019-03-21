package com.launchcode.bonjour.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class BonjourController {
    private static HashMap<String, String> greetings = new HashMap<>();
    static {
        greetings.put("english", "Hello");
        greetings.put("portuguese", "Oi");
        greetings.put("french", "Bonjour");
        greetings.put("spanish", "Hola");
        greetings.put("binary", "01001000 01100101 01101100 01101100 01101111");
    }

    private static String createLanguageOptions() {
        StringBuilder options = new StringBuilder();

        for(String language : greetings.keySet()) {
            String option = "<option value=" + language + ">" + language + "</option>";
            options.append(option);
            options.append("\n");
        }

        return options.toString();
    }

    private static String createLanguagesSelect() {
        String output = "<select name='language'>\n";
        output += createLanguageOptions();
        output += "</select>";

        return output;
    }

    private static String createNameInput() {
        return "<input type='text' name='name' placeholder='your name here' />";
    }

    private static String createSubmitButton() {
        return "<input type='submit' value='Greet me!' />";
    }

    private static String createBonjourForm() {
        String output = "<form action='/' method='POST'>";
        output += createNameInput();
        output += createLanguagesSelect();
        output += createSubmitButton();
        output += "</form>";

        return output;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return createBonjourForm();
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String submitHandler(@RequestParam String name, @RequestParam String language) {
        String greeting = greetings.get(language);
        if (greeting == null) {
            return "Greeting not found";
        }

        return greeting + " " + name;
    }
}
