package com.agapovp.address.view;

import com.agapovp.address.model.Person;
import com.agapovp.address.util.DateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage  = new StringBuilder("");

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage.append("No valid first name!\n");
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage.append("No valid last name!\n");
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage.append("No valid street!\n");
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage.append("No valid city!\n");
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage.append("No valid postal code!\n");
        } else {
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("No valid postal code format (must be a number)!\n");
            }
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage.append("No valid birthday!\n");
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage.append("No valid birthday format. Please use format dd.mm.yyyy!\n");
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Field(s)");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage.toString());

            alert.showAndWait();

            return false;
        }
    }
}
