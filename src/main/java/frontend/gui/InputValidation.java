package frontend.gui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.InstallSolarPanels;
import data.LoginDetails;
import data.User;

import frontend.controllers.ActivitiesController;
import frontend.controllers.EditProfilePopUpController;
import frontend.controllers.FriendRequestController;
import frontend.controllers.FriendspageController;
import frontend.controllers.HomepageController;
import frontend.controllers.NavPanelController;
import frontend.controllers.NotificationPanelController;
import frontend.controllers.ProfilePageController;
import frontend.controllers.QuestionnaireController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tools.DateUtils;
import tools.InputValidationTool;
import tools.Requests;

import java.io.IOException;
import java.time.temporal.ChronoUnit;


public class InputValidation {



    /**
     * .
     * Validation for signing in
     *
     * @param emailField email input field
     * @param passField  password input field
     */
    public static boolean signInValidate(TextField emailField,
                                      PasswordField passField) throws IOException {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        User loggedUser = Requests.instance.loginRequest(loginDetails);

        // update user's CO2 saved from InstallSolarPanels activity
        if (loggedUser != null) {
            if (loggedUser.getSimilarActivities(new InstallSolarPanels()).size() > 0) {
                InstallSolarPanels panels = (InstallSolarPanels) loggedUser
                        .getSimilarActivities(new InstallSolarPanels()).get(0);
                double extraCo2Saved = ChronoUnit.DAYS.between(
                        loggedUser.getLastLoginDate().toInstant(),
                        DateUtils.instance.dateToday().toInstant())
                        * panels.getDailyCarbonSaved();
                double newValue = loggedUser.getTotalCarbonSaved() + extraCo2Saved;
                Requests.instance.editProfile(loginDetails,
                        "totalCarbonSaved",
                        newValue);
            }
        }

        if (loggedUser != null) {
            //prompt the user with a dialog popup message
            Dialog.show("Login successful", "Welcome to GoGreen, "
                    + loggedUser.getFirstName()
                    + " " + loggedUser.getLastName() + "!", "DISMISS", "sucess", false);

            //Set the login details & user to the required page controllers
            HomepageController.setUser(loggedUser);
            HomepageController.setLoginDetails(loginDetails);
            ActivitiesController.setUser(loggedUser);
            ActivitiesController.setLoginDetails(loginDetails);
            FriendspageController.setUser(loggedUser);
            FriendspageController.setLoginDetails(loginDetails);
            ProfilePageController.setUser(loggedUser);
            ProfilePageController.setLoginDetails(loginDetails);
            NotificationPanelController.setUser(loggedUser);
            NotificationPanelController.setLoginDetails(loginDetails);
            EditProfilePopUpController.setUser(loggedUser);
            EditProfilePopUpController.setLoginDetails(loginDetails);
            NavPanelController.setLoginDetails(loginDetails);
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
            //prompt user with a dialog popup for failing to login
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
     * @param primaryFields fields containing username & email
     * @param ageField      User's age field
     */
    public static boolean signUpValidate(JFXTextField[] nameFields,
                                      JFXTextField[] primaryFields,
                                      JFXPasswordField[] passFields, JFXTextField ageField,
                                      int secQuestionId, JFXTextField secAnswer)
            throws IOException {

        if (!signUpValidateFields(nameFields, primaryFields[1], secAnswer)) {
            return false;
        }
        if (!signUpValidatePass(primaryFields[0], passFields, ageField)) {
            return false;
        }

        //send requests to the server to see if username and password already exist
        //before proceeding to the questionnaire page
        String username = primaryFields[1].getText();
        String email = primaryFields[0].getText();

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
                Integer.parseInt(ageField.getText()), primaryFields[0].getText(),
                primaryFields[1].getText(), passFields[0].getText());
        user.setSecurityQuestionAnswer(secAnswer.getText());
        user.setSecurityQuestionId(secQuestionId);

        QuestionnaireController.setUser(user);
        StageSwitcher.signInUpSwitch(Main.getPrimaryStage(), Main.getQuestionnaire());

        return true;
    }

    private static boolean signUpValidateFields(JFXTextField[] nameFields,
                                                JFXTextField usernameField,
                                                JFXTextField secAnswer) throws IOException {
        if (nameFields[0].getText() == null || nameFields[0].getText().equals("")) {
            Dialog.show("Form Error!", "Please enter your First Name",
                    "DISMISS", "error", false);
            return false;
        }
        if (nameFields[1].getText() == null || nameFields[1].getText().equals("")) {
            Dialog.show("Form Error!", "Please enter your Last Name", "DISMISS", "error", false);
            return false;
        }
        if (usernameField.getText() == null || usernameField.getText().equals("")) {
            Dialog.show("Form Error!", "Please enter a username", "DISMISS", "error", false);
            return false;
        }
        if (secAnswer.getText() == null || secAnswer.getText().equals("")) {
            Dialog.show("Form Error!", "Please enter an answer", "DISMISS", "error", false);
            return false;
        }
        return true;
    }

    private static boolean signUpValidatePass(JFXTextField emailField,
                                              JFXPasswordField[] passFields,
                                              JFXTextField ageField) throws IOException {
        if (emailField.getText() == null
                || !InputValidationTool.validateEmail(emailField.getText())) {
            Dialog.show("Form Error!", "Please enter a valid email", "DISMISS", "error", false);
            return false;
        }

        if (passFields[0].getText() == null
                || !InputValidationTool.validatePassword(passFields[0].getText())) {
            Dialog.show("Form Error!", "Please enter a valid password", "DISMISS", "error", false);
            return false;
        }
        if (passFields[1].getText() == null || passFields[1].getText().equals("")
                || !passFields[1].getText().equals(passFields[0].getText())) {
            Dialog.show("Form Error!", "Passwords do not match", "DISMISS", "error", false);
            return false;
        }
        if (ageField.getText() == null || !InputValidationTool.validateAge(ageField.getText())) {
            Dialog.show("Form Error!", "Please enter a valid age", "DISMISS", "error", false);
            return false;
        }
        return true;
    }

}