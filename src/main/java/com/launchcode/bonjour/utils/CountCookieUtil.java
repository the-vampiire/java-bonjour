package com.launchcode.bonjour.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CountCookieUtil {
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
        String urlEncodedName = urlEncodeName(name);
        String countString = String.valueOf(greetingsCount);

        return new Cookie(urlEncodedName, countString);
    }

    /**
     * URL encodes a cookie name to prevent reserved token error
     * @param name name to URL encode
     * @return a URL encoded name or "generic" if encoding fails
     */
    private static String urlEncodeName(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch(UnsupportedEncodingException error) {
            System.out.println("Failed to URL encode: " + name);
            return "generic";
        }
    }

    /**
     * Parses request cookies for the given User name and increments the greeting count
     * <ul>
     *   <li>
     *     compares name in URL encoded form
     *   </li>
     * </ul>
     * @param request Request object to read available cookies
     * @param name User input
     * @return the incremented greetings count
     */
    public static int getGreetingCount(HttpServletRequest request, String name) {
        int greetingsCount = 0; // initialize at 0 or replaced in loop
        String urlEncodedName = urlEncodeName(name);
        Cookie[] cookies = request.getCookies(); // may be null if the User has no cookies

        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(urlEncodedName)) {
                    String countString = cookie.getValue();
                    greetingsCount = Integer.parseInt(countString);
                }
            }
        }

        return ++greetingsCount;
    }

    /**
     * Attaches a response cookie with the new greeting count for the User name
     * @param response Response object to attach the cookie
     * @param name User inputted name (URL encoded as cookie name)
     * @param greetingsCount the current greeting count for the given User name
     */
    public static void attachGreetingCountCookie(HttpServletResponse response, String name, int greetingsCount) {
        Cookie countCookie = createCountCookie(name, greetingsCount);
        response.addCookie(countCookie);
    }
}
