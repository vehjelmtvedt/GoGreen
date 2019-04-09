package frontend.gui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.InstallSolarPanels;
import data.LoginDetails;
import data.User;

import frontend.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tools.Requests;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    private static final String passPattern =
            "((?=.*[a-z]).{6,15})";
    private static final String emailPattern =
            "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";


    /**
     * .
     * Validation for signing in
     *
     * @param emailField email input field
     * @param passField  password input field
     * @param form       form containing input fields
     */
    public static boolean signInValidate(TextField emailField,
                                      PasswordField passField, AnchorPane form) throws IOException {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        User loggedUser = Requests.instance.loginRequest(loginDetails);

        // update user's CO2 saved from InstallSolarPanels activity
        if (loggedUser != null) {
            if (loggedUser.getSimilarActivities(new InstallSolarPanels()).size() > 0) {
                InstallSolarPanels panels = (InstallSolarPanels) loggedUser
                        .getSimilarActivities(new InstallSolarPanels()).get(0);
                double extraCo2Saved = ChronoUnit.DAYS.between(
                        loggedUser.getLastLoginDate().toInstant(),
                        Calendar.getInstance().getTime().toInstant())
                        * panels.getDailyCarbonSaved();
                double newValue = loggedUser.getTotalCarbonSaved() + extraCo2Saved;
                Requests.instance.editProfile(loginDetails,
                        "totalCarbonSaved",
                        newValue);
            }
        }

        if (loggedUser != null) {
            Dialog.show("Login successful", "Welcome to GoGreen, "
                    + loggedUser.getFirstName()
                    + " " + loggedUser.getLastName() + "!", "DISMISS", "sucess", false);
            HomepageController.setUser(loggedUser);
            HomepageController.setLoginDetails(loginDetails);
            ActivitiesController.setUser(loggedUser);
            FriendspageController.setUser(loggedUser);
            FriendspageController.setLoginDetails(loginDetails);
            ProfilePageController.setUser(loggedUser);
            NotificationPanelController.setUser(loggedUser);
            NotificationPanelController.setLoginDetails(loginDetails);
            EditProfilePopUpController.setUser(loggedUser);
            EditProfilePopUpController.setLoginDetails(loginDetails);
            FriendRequestController.setThisUsername(loggedUser.getUsername());

            //setup .fxml pages after successfully logging in
            try {
                FXMLLoader loader1 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/Homepage.fxml"));
                FXMLLoader loader2 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/Activities.fxml"));
                FXMLLoader loader3 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/FriendPage.fxml"));
                FXMLLoader loader4 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/ProfilePage.fxml"));
                Parent root1 = loader1.load();
                Parent root2 = loader2.load();
                Parent root3 = loader3.load();
                Parent root4 = loader4.load();
                Scene homepage = new Scene(root1, General.getBounds()[0], General.getBounds()[1]);
                Scene activities = new Scene(root2, General.getBounds()[0], General.getBounds()[1]);
                Scene friendPage = new Scene(root3, General.getBounds()[0], General.getBounds()[1]);
                Scene profilePage = new Scene(
                        root4, General.getBounds()[0], General.getBounds()[1]
                );

                //setup scenes in main class
                Main.setActivities(activities);
                Main.setHomepage(homepage);
                Main.setFriendPage(friendPage);
                Main.setProfilePage(profilePage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Go to homepage after logging in
            StageSwitcher.signInUpSwitch(Main.getPrimaryStage(), Main.getHomepage());
            return true;
        } else {
            Dialog.show("Login failed",
                    "Incorrect credentials. Try again", "DISMISS", "error", false);
            return false;
        }
    }

    /**
     * .
     * Validation for input in sign up form
     *
     * @param nameFields    array of fields containing the user's name
     * @param usernameField User's username name field
     * @param emailField    User's email field
     * @param passField     User's password field
     * @param passReField   User's re-password field
     * @param ageField      User's age field
     */
    public static boolean signUpValidate(JFXTextField[] nameFields,
                                      JFXTextField usernameField, JFXTextField emailField,
                                      JFXPasswordField passField, JFXPasswordField passReField,
                                      JFXTextField ageField,
                                      int secQuestionId,
                                      JFXTextField secAnswer) throws IOException {

        if (!signUpValidateFields(nameFields, usernameField, secAnswer)) {
            return false;
        }
        if (!signUpValidatePass(emailField, passField, passReField, ageField)) {
            return false;
        }

        //send requests to the server to see if username and password already exist
        //before proceeding to the questionnaire page
        String username = usernameField.getText();
        String email = emailField.getText();

        if (Requests.instance.validateUserRequest(username)) {
            Dialog.show("Username Error!",
                    "A user already exists with this username. Use another username",
                    "DISMISS", "error", false);
            return false;
        }

        if (Requests.instance.validateUserRequest(email)) {
            Dialog.show("Email Error!", "A user already exists with this email."
                            + "Use another email",
                    "DISMISS", "error", false);
            return false;
        }

        if (secQuestionId == -1) {
            Dialog.show("Security Question Error", "You did not specify your security question",
                    "DISMISS", "error", false);
            return false;
        }

        User user = new User(nameFields[0].getText(),
                nameFields[1].getText(),
                Integer.parseInt(ageField.getText()), emailField.getText(),
                usernameField.getText(), passField.getText());
        user.setSecurityQuestionAnswer(secAnswer.getText());
        user.setSecurityQuestionId(secQuestionId);

        QuestionnaireController.setUser(user);
        StageSwitcher.signInUpSwitch(Main.getPrimaryStage(), Main.getQuestionnaire());
        return true;
    }

    private static boolean signUpValidateFields(JFXTextField[] nameFields,
                                                JFXTextField usernameField,
                                                JFXTextField secAnswer) throws IOException {
        if (nameFields[0].getText().isEmpty()) {
            Dialog.show("Form Error!", "Please enter your First Name",
                    "DISMISS", "error", false);
            return false;
        }
        if (nameFields[1].getText().isEmpty()) {
            Dialog.show("Form Error!", "Please enter your Last Name", "DISMISS", "error", false);
            return false;
        }
        if (usernameField.getText().isEmpty()) {
            Dialog.show("Form Error!", "Please enter a username", "DISMISS", "error", false);
            return false;
        }
        if (secAnswer.getText().isEmpty()) {
            Dialog.show("Form Error!", "Please enter an answer", "DISMISS", "error", false);
            return false;
        }
        return true;
    }

    private static boolean signUpValidatePass(JFXTextField emailField,
                                              JFXPasswordField passField,
                                              JFXPasswordField passReField,
                                              JFXTextField ageField) throws IOException {
        if (emailField.getText().isEmpty() || !validateEmail(emailField)) {
            Dialog.show("Form Error!", "Please enter a valid email", "DISMISS", "error", false);
            return false;
        }

        if (passField.getText().isEmpty() || !validatePassword(passField)) {
            Dialog.show("Form Error!", "Please enter a valid password", "DISMISS", "error", false);
            return false;
        }
        if (passReField.getText().isEmpty() || !passReField.getText().equals(passField.getText())) {
            Dialog.show("Form Error!", "Passwords do not match", "DISMISS", "error", false);
            return false;
        }
        if (ageField.getText().isEmpty() || !validateAge(ageField)) {
            Dialog.show("Form Error!", "Please enter a valid age", "DISMISS", "error", false);
            return false;
        }
        return true;
    }

    private static boolean validatePassword(JFXPasswordField input) {
        String pass = input.getText();
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(pass);

        return matcher.matches();
    }

    private static boolean validateEmail(JFXTextField input) {
        String email = input.getText();
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private static boolean validateAge(TextField input) {
        try {
            int age = Integer.parseInt(input.getText());
            return age >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}