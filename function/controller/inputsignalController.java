package function.controller;

import function.Table.inputsignalTable;
import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * Created by BELYANIN on 31.01.2018.
 */
public class inputsignalController implements Initializable {

    private DBSql dbselect = new DBSql("localhost", "5439", "postgres" , "postgres", "postgres");
    private ObservableList<String> listFindkks = FXCollections.observableArrayList();
    private static ObservableList<inputsignalTable> data;

    @FXML
    private TableView<inputsignalTable> inputsignalTable;

    @FXML
    private TableColumn<inputsignalTable, String> pageleft;

    @FXML
    private TableColumn<inputsignalTable, String> pageleftno;

    @FXML
    private TableColumn<inputsignalTable, String> signalcodein;

    @FXML
    private TableColumn<inputsignalTable, String> telegramin;

    @FXML
    private TableColumn<inputsignalTable, Integer> abonent_id;

    @FXML
    private TableColumn<inputsignalTable, String> page_kks;

    @FXML
    private TableColumn<inputsignalTable, String> page_no;

    private String kksfind = null;

    private showAlert showAlert = new showAlert();

    private simtableController simtableController;

    private String valcheckbox;

    public inputsignalController() throws SQLException, ClassNotFoundException {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    //---------Сохраняем выделенное в файл
    public void saveToFile(ActionEvent event){
        try {

            FileChooser fileChooserSaveAll = new FileChooser();
            File file = fileChooserSaveAll.showSaveDialog(new Stage());
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooserSaveAll.getExtensionFilters().add(extFilter);
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);

            StringBuilder clipboardString = new StringBuilder();
            int colN = 0;
            //Считываем намименование столбцов в таблице
            for (TableColumn p : inputsignalTable.getColumns()){
                //System.out.println(p.getText());
                clipboardString.append(p.getText());
                if (colN < inputsignalTable.getColumns().size()-1){
                    clipboardString.append("\t");
                }
                colN++;
            }
            clipboardString.append('\n');

            ObservableList<TablePosition> posList = inputsignalTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                int r = p.getRow();
                int c = p.getColumn();
                //System.out.println("row " + r + " col " + c);

                clipboardString.append(inputsignalTable.getItems().get(r).getPageleft());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getPageleftno());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getSignalcodein());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getTelegramin());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getAbonent_id());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getPage_kks());
                clipboardString.append('\t');
                clipboardString.append(inputsignalTable.getItems().get(r).getPage_no());
                clipboardString.append('\n');

            }

            fileWriter.write(String.valueOf(clipboardString));
            fileWriter.close();
        }
        catch (IOException e){

        }
    }

    @FXML
    public void OnMouseClicked(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

        if(event.getClickCount()>1){
            ObservableList<TablePosition> posList = inputsignalTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            int c = -1;
            int r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                r = p.getRow();
                c = p.getColumn();
                //System.out.println("row " + r + " col " + c);

                switch (c){
                    case 0:
                        kksfind = inputsignalTable.getItems().get(c).getPageleft();
                        break;
                    default:
                        break;
                }
            }


            Stage stage = new Stage();
            StackPane stackPane = new StackPane();
            HBox hBox;
            VBox vBox;
            Scene scene;
            TableView inputsignalPane;
            TableView outputsignalPane;
            TableView connectionPane;
            FXMLLoader fxmlLoader;

            fxmlLoader = new FXMLLoader(getClass().getResource("/menu/inputsignal.fxml"));
            inputsignalPane = fxmlLoader.load();
            inputsignalController inputsignalController = fxmlLoader.getController();
            inputsignalController.setoption(valcheckbox,kksfind);
            inputsignalController.setSimtableController(simtableController);

            fxmlLoader = new FXMLLoader(getClass().getResource("/menu/outputsignal.fxml"));
            outputsignalPane = fxmlLoader.load();
            outputsignalController outputsignalController = fxmlLoader.getController();
            outputsignalController.setSimtableController(simtableController);
            outputsignalController.setoption(valcheckbox, kksfind);

            fxmlLoader = new FXMLLoader(getClass().getResource("/menu/connectiontable.fxml"));
            connectionPane  = fxmlLoader.load();
            connectiontableController connectiontableController = fxmlLoader.<connectiontableController>getController();
            connectiontableController.setoption(valcheckbox, null);
            connectiontableController.setSimtableController(simtableController);

            int flag = 1;
            switch (valcheckbox) {
                case "[false, false, false, false]" :
                    System.out.println("false, false, false, false");
                    showAlert.warningAlert("Выбери опцию поиска",null,"Поставить соответсвующую галочку для поиска данных");
                    break;
                case "[true, false, false, false]" :

                    stackPane.getChildren().add(inputsignalPane);

                    break;
                case "[false, true, false, false]" :

                    stackPane.getChildren().add(outputsignalPane);

                    break;
                case "[false, false, true, false]" :

                    stackPane.getChildren().add(outputsignalPane);

                    break;
                case "[false, false, false, true]" :

                    stackPane.getChildren().add(connectionPane);

                    break;
                case "[true, true, false, true]" :
                    hBox = new HBox();
                    vBox = new VBox();

                    hBox.getChildren().add(inputsignalPane);

                    hBox.getChildren().add(outputsignalPane);

                    vBox.getChildren().add(hBox);

                    vBox.getChildren().add(connectionPane);

                    stackPane.getChildren().add(vBox);

                    break;
                case "[true, false, false, true]" :
                    hBox = new HBox();

                    hBox.getChildren().add(inputsignalPane);

                    hBox.getChildren().add(connectionPane);

                    stackPane.getChildren().add(hBox);

                    break;
                case "[false, true, false, true]" :
                    hBox = new HBox();

                    hBox.getChildren().add(connectionPane);

                    hBox.getChildren().add(outputsignalPane);

                    stackPane.getChildren().add(hBox);


                    break;
                case "[true, true, true, false]" :
                    break;
                case "[true, true, true, true]" :
                    break;
                default :
                    flag = 0;
                    showAlert.infromationAlert("Опции поиска",null,"Такой комбинации опции поиска не существует");
                    //JOptionPane.showMessageDialog(new JOptionPane(),"Такой комбинации опции поиска не существует","Опции поиска",JOptionPane.WARNING_MESSAGE);
                    break;
            }

            if (flag == 1){
                stage.setTitle(kksfind);
                scene = new Scene(stackPane);
                stage.setScene(scene);
                stage.show();
            }




        }
    }

    //-------Инициализация передаваемых настроек
    public void setoption(String valcheckbox, String kksfind) {

        this.kksfind = kksfind;
        this.valcheckbox = valcheckbox;

        inputsignalTable.getSelectionModel().setCellSelectionEnabled(true);
        inputsignalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Задаем параметры для столбцов таблицы
        pageleft.setCellValueFactory(new PropertyValueFactory<>("pageleft"));
        pageleftno.setCellValueFactory(new PropertyValueFactory<>("pageleftno"));
        signalcodein.setCellValueFactory(new PropertyValueFactory<>("signalcodein"));
        telegramin.setCellValueFactory(new PropertyValueFactory<>("telegramin"));
        abonent_id.setCellValueFactory(new PropertyValueFactory<>("abonent_id"));
        page_kks.setCellValueFactory(new PropertyValueFactory<>("page_kks"));
        page_no.setCellValueFactory(new PropertyValueFactory<>("page_no"));

        try {
            dbselect.SQLAInputSignal(kksfind);


            data = FXCollections.observableArrayList();

            while (dbselect.resultSet.next()){
                data.add(new inputsignalTable(dbselect.resultSet.getString(1),dbselect.resultSet.getString(2),dbselect.resultSet.getString(3),dbselect.resultSet.getString(4),dbselect.resultSet.getInt(5), dbselect.resultSet.getString(6),dbselect.resultSet.getString(7)));
                inputsignalTable.setItems(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setSimtableController(simtableController simtableController) {
        this.simtableController = simtableController;
    }

}
