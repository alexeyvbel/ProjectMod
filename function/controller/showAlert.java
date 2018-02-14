package function.controller;

import javafx.scene.control.Alert;

/**
 * Created by BELYANIN on 05.02.2018.
 */
public class showAlert {

    public void showAlert(){

    }

    public void warningAlert(String titleText, String headerText, String contentText) {
       Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setTitle(titleText);
       alert.setHeaderText(headerText);
       alert.setContentText(contentText);
       alert.showAndWait();
    }

    public void infromationAlert(String titleText, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void errorAlert(String titleText, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
