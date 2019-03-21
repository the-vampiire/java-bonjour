package com.launchcode.bonjour.controllers;


import models.Bonjour;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class BonjourController {
    /**
     * Creates a cookie with the greetingsCount assigned to the inputted name
     * <ul>
     *   <li>
     *     protects against reserved token cookie names by URL encoding the User inputted name
     *   </li>
     *   <li>
     *      if URL encoding fails sets Cookie name as "generic"
     *   </li>
     * </ul>
     * @param name User input
     * @param greetingsCount count of how many times this User has been greeted
     * @return greetings count cookie for the given User name
     */
    private static Cookie createCountCookie(String name, int greetingsCount) {
        String urlEncodedName;
        String countString = String.valueOf(greetingsCount);


        // cookies may not have spaces in them!
        // https://ping.force.com/Support/Cookie-name-is-a-reserved-token
        try {
            urlEncodedName = URLEncoder.encode(name, "UTF-8");
        } catch(UnsupportedEncodingException error) {
            urlEncodedName = "generic";
            System.out.println("Failed to URL encode: " + name);
        }

        return new Cookie(urlEncodedName, countString);
    }

    /**
     * Parses request cookies and attaches a greetings count cookie to the response
     * @param request Request object to read available cookies
     * @param response Response object to attach the cookie
     * @param name User input
     * @return the incremented greetings count
     */
    private static int processAndAttachCountCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        int greetingsCount = 0; // initialize at 0 or replaced in loop
        Cookie[] cookies = request.getCookies(); // may be null if the User has no cookies

        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    String countString = cookie.getValue();
                    greetingsCount = Integer.parseInt(countString);
                }
            }
        }


        ++greetingsCount;

        Cookie countCookie = createCountCookie(name, greetingsCount);
        response.addCookie(countCookie);

        return greetingsCount;
    }

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

        int greetingsCount = processAndAttachCountCookie(request, response, name);

        return greeting + " " + name + " (you have been greeted: " + greetingsCount + " times)";
    }
}
