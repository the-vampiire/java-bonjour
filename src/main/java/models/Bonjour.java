package models;

import java.util.HashMap;

public class Bonjour {
    private static HashMap<String, String> greetings = new HashMap<>();
    static {
        greetings.put("english", "Hello");
        greetings.put("portuguese", "Oi");
        greetings.put("french", "Bonjour");
        greetings.put("spanish", "Hola");
        greetings.put("binary", "01001000 01100101 01101100 01101100 01101111");
    }

    private static String bonjourForm = createBonjourForm();

    public static String getGreeting(String language) {
        return greetings.get(language);
    }

    public static String getBonjourForm() {
        return bonjourForm;
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
}
