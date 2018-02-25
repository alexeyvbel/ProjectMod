package function.controller;

import function.Table.inputsignalTable;
import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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


    public inputsignalController() throws SQLException, ClassNotFoundException {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            dbselect.SQLAInputSignal();


        data = FXCollections.observableArrayList();

        while (dbselect.resultSet.next()){
            data.add(new inputsignalTable(dbselect.resultSet.getString(1),dbselect.resultSet.getString(2),dbselect.resultSet.getString(3),dbselect.resultSet.getString(4),dbselect.resultSet.getInt(5), dbselect.resultSet.getString(6),dbselect.resultSet.getString(7)));
            inputsignalTable.setItems(data);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void OnMouseClicked(MouseEvent event){

        if(event.getClickCount()>1){
            ObservableList<TablePosition> posList = inputsignalTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                int r = p.getRow();
                int c = p.getColumn();
                System.out.println("row " + r + " col " + c);
            }

        }
    }

}
