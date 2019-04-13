package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationTool {

    private static final String passPattern =
            "((?=.*[a-z]).{6,15})";
    private static final String emailPattern =
            "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";

    /**
     * validates password.
     * @param pass password
     * @return true/false
     */
    public static boolean validatePassword(String pass) {
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(pass);

        return matcher.matches();
    }

    /**
     * validates email.
     * @param email email
     * @return true/false
     */
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * validates age.
     * @param input age
     * @return true/false
     */
    public static boolean validateAge(String input) {
        try {
            int age = Integer.parseInt(input);
            return age >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

