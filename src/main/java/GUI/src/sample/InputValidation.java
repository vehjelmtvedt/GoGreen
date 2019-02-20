package GUI.src.sample;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static boolean validateAge(TextField input, String message){
        try{
            int age = Integer.parseInt(input.getText());
            if(age >= 0){
                System.out.println("User's age is: " + age);
                return true;
            }
            System.out.println("Error: " + message + " is not a valid number");
            return false;
        }catch (NumberFormatException e){
            System.out.println("Error: " + message + " is not a number");
            return false;
        }
    }

    public static boolean validatePassword(TextField input, String message){
        String pass = input.getText();

//        Pattern p = Pattern.compile("((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})");
//        Matcher m = p.matcher(pass);
//        if(m.matches()){
//            System.out.println("Password is: " +  pass);
//        }
//        System.out.println("Error: " + pass + " is not a valid password");
//        return false;
        if(pass.length() >=6 && pass.length() <= 15){
            System.out.println("Password is: " +  pass);
            return true;
        }
        System.out.println("Error: " + pass + " is not a valid password");
        return false;
    }
}
