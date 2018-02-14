package function.controller;


import function.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Handler;

public class Controller {
    @FXML
    private MenuItem menuItemConnection;

    private Pane panefindkks;
    private Pane paneSimulation;
    private BorderPane borderPane;
    private AnchorPane panecreateselect;
    private simtableController controller;
    private findkksController findkksController;
    private connectiontableController connectiontableController;

    Main main = new Main();
    static Stage stage = new Stage();


    @FXML
    public void showSceneFindKks(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderSimulaton = new FXMLLoader();
        fxmlLoaderSimulaton.setLocation(getClass().getResource("/menu/simulation.fxml"));
        paneSimulation = fxmlLoaderSimulaton.load();
        controller = fxmlLoaderSimulaton.getController();

        FXMLLoader fxmlLoaderfindkks = new FXMLLoader();
        fxmlLoaderfindkks.setLocation(getClass().getResource("/menu/findkks.fxml"));
        panefindkks = fxmlLoaderfindkks.load();
        findkksController = fxmlLoaderfindkks.getController();
        findkksController.setSimtableController(controller);

        borderPane = Main.getRoot();

        BorderPane.setAlignment(panefindkks, Pos.TOP_CENTER);
        borderPane.setCenter(panefindkks);
        borderPane.setRight(paneSimulation);

    }

    @FXML
    public void showSceneSelect (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderSelect = new FXMLLoader();
        borderPane = Main.getRoot();
        fxmlLoaderSelect.setLocation(getClass().getResource("/menu/createselect.fxml"));
        panecreateselect = fxmlLoaderSelect.load();

        BorderPane.setAlignment(panecreateselect, Pos.TOP_LEFT);
        borderPane.setCenter(panecreateselect);
        borderPane.setRight(null);


    }

    public void addDatatoSimulationTable(){
        //System.out.println("Пробуем записать данные в таблицу");
        //controller.addDatatoTable();
    }

    public void closeMainWin(){
        main.closeStage();
    }

    public void closeStage(){
        stage.close();
    }


}


