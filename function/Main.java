package function;/**
 * Created by BELYANIN on 10.01.2018.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

;

public class Main extends Application {
    private static BorderPane root = new BorderPane();
    static Stage primaryStage;
    static Scene scene;


    public static BorderPane getRoot(){
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane bar = FXMLLoader.load(getClass().getResource("/menu/MenuBar.fxml"));
        //AnchorPane panefindkks = FXMLLoader.load(getClass().getResource("findkks.fxml"));
        //root.setBottom(panefindkks);
        root.setTop(bar);
        this.primaryStage = primaryStage;
        scene = new Scene(root, 1100, 700);
        this.primaryStage.setTitle("BigData");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

    public void closeStage(){
        primaryStage.close();
    }
}
