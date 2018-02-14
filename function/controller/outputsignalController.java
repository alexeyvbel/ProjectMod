package function.controller;


import function.Table.outputsignalTable;
import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by BELYANIN on 01.02.2018.
 */
public class outputsignalController {
    private DBSql dbselect = new DBSql("localhost", "5439", "postgres" , "postgres", "postgres");
    private ObservableList<String> listFindkks = FXCollections.observableArrayList();
    private static ObservableList<outputsignalTable> data;


    @FXML
    private TableColumn<outputsignalTable, Integer> abonent_id;

    @FXML
    private TableColumn<outputsignalTable, String> page_kks;

    @FXML
    private TableColumn<outputsignalTable, String> page_no;

    @FXML
    private TableColumn<outputsignalTable, String> signalcode;

    @FXML
    private TableColumn<outputsignalTable, String> telegram;

    @FXML
    private TableColumn<outputsignalTable, String> pageright;

    @FXML
    private TableColumn<outputsignalTable, String> pageright_no;

    @FXML
    private TableColumn<outputsignalTable, String> block;

    @FXML
    private TableColumn<outputsignalTable, String> techname;

    @FXML
    private TableColumn<outputsignalTable, String> marker;

    @FXML
    private TableView<outputsignalTable> outputsignalTable;

    public outputsignalController() throws SQLException, ClassNotFoundException {

    }

    public void initialize() throws IOException, SQLException, Exception {

        outputsignalTable.getSelectionModel().setCellSelectionEnabled(false);
        outputsignalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Задаем параметры для столбцов таблицы
        abonent_id.setCellValueFactory(new PropertyValueFactory<>("abonent_id"));
        page_kks.setCellValueFactory(new PropertyValueFactory<>("page_kks"));
        page_no.setCellValueFactory(new PropertyValueFactory<>("page_no"));
        signalcode.setCellValueFactory(new PropertyValueFactory<>("signalcode"));
        telegram.setCellValueFactory(new PropertyValueFactory<>("telegram"));
        pageright.setCellValueFactory(new PropertyValueFactory<>("pageright"));
        pageright_no.setCellValueFactory(new PropertyValueFactory<>("pageright_no"));
        block.setCellValueFactory(new PropertyValueFactory<>("block"));
        techname.setCellValueFactory(new PropertyValueFactory<>("techname"));
        marker.setCellValueFactory(new PropertyValueFactory<>("marker"));

        dbselect.SQLOutputSignal();

        data = FXCollections.observableArrayList();

        while (dbselect.resultSet.next()){
            data.add(new outputsignalTable(dbselect.resultSet.getInt(1),dbselect.resultSet.getString(2),dbselect.resultSet.getString(3),dbselect.resultSet.getString(4),dbselect.resultSet.getString(5), dbselect.resultSet.getString(6),dbselect.resultSet.getString(7),dbselect.resultSet.getString(8),dbselect.resultSet.getString(9),dbselect.resultSet.getString(10)));
            outputsignalTable.setItems(data);
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
            for (TableColumn p : outputsignalTable.getColumns()){
                //System.out.println(p.getText());
                clipboardString.append(p.getText());
                if (colN < outputsignalTable.getColumns().size()-1){
                    clipboardString.append("\t");
                }
                colN++;
            }
            clipboardString.append('\n');

            ObservableList<TablePosition> posList = outputsignalTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                int r = p.getRow();
                int c = p.getColumn();
                //System.out.println("row " + r + " col " + c);

                clipboardString.append(outputsignalTable.getItems().get(r).getAbonent_id());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getPage_kks());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getPage_no());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getSignalcode());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getTechname());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getPageright());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getPageright_no());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getBlock());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getTechname());
                clipboardString.append('\t');
                clipboardString.append(outputsignalTable.getItems().get(r).getMarker());
                clipboardString.append('\n');

            }

            fileWriter.write(String.valueOf(clipboardString));
            fileWriter.close();
        }
        catch (IOException e){

        }
    }

}
