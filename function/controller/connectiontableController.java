package function.controller;

import function.Table.connectionTable;
import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by BELYANIN on 02.02.2018.
 */
public class connectiontableController implements Initializable {

    private DBSql dbselect = new DBSql("localhost", "5439", "postgres" , "postgres", "postgres");
    private ObservableList<String> listFindkks = FXCollections.observableArrayList();
    private static ObservableList<connectionTable> data;
    private String valcheckbox;


    private String massage;
    //private StringProperty valcheckbox = new SimpleStringProperty();

    @FXML
    private TableView<connectionTable> connectionTable;

    @FXML
    private TableColumn<connectionTable, String> cabinet;

    @FXML
    private TableColumn<connectionTable, String> kks;

    @FXML
    private TableColumn<connectionTable, String> page_no;

    @FXML
    private TableColumn<connectionTable, String> page_esg;

    @FXML
    private TableColumn<connectionTable, String> page_nobi;

    @FXML
    private TableColumn<connectionTable, String> value;

    @FXML
    private TableColumn<connectionTable, String> addr;

    @FXML
    private TableColumn<connectionTable, String> het;

   // private ContextMenu contextMenu = new ContextMenu();
   // private MenuItem itemSaveAll = new MenuItem("Сохранить все в файл");

   // private MenuItem itemSave = new MenuItem("Сохранить в файл");
   // private MenuItem itemAddImitaion = new MenuItem("Имитация");

    private simtableController simtableController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionTable.getSelectionModel().setCellSelectionEnabled(false);
        connectionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public connectiontableController() throws SQLException, ClassNotFoundException {

    }

    public String getValcheckbox() {
        return valcheckbox;
    }

    public void setValcheckbox(String valuecheckbox) throws SQLException, ClassNotFoundException {

        //contextMenu.getItems().addAll(itemSaveAll, itemSave, itemAddImitaion);

        //Задаем параметры для столбцов таблицы
        cabinet.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        kks.setCellValueFactory(new PropertyValueFactory<>("kks"));
        page_no.setCellValueFactory(new PropertyValueFactory<>("page_no"));
        page_esg.setCellValueFactory(new PropertyValueFactory<>("page_esg"));
        page_nobi.setCellValueFactory(new PropertyValueFactory<>("page_nobi"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        addr.setCellValueFactory(new PropertyValueFactory<>("addr"));
        het.setCellValueFactory(new PropertyValueFactory<>("het"));

        this.valcheckbox = valuecheckbox;

        try {
        dbselect.SQLConnectionTable(valcheckbox);
        data = FXCollections.observableArrayList();

        while (dbselect.resultSet.next()){
            data.add(new connectionTable(dbselect.resultSet.getString(1),dbselect.resultSet.getString(2),dbselect.resultSet.getString(3),dbselect.resultSet.getString(4), dbselect.resultSet.getString(5), dbselect.resultSet.getString(6), dbselect.resultSet.getString(7), dbselect.resultSet.getString(8)));
            connectionTable.setItems(data);
         }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    //---------Добавляем данные в таблицу имитаций
    public void addToSimulationTable(ActionEvent event) {
        ObservableList<TablePosition> posList1 = connectionTable.getSelectionModel().getSelectedCells();

        //Считываем данные из таблицы
        for (TablePosition p : posList1) {
            int r = p.getRow();

            simtableController.addDatatoTable(connectionTable.getItems().get(r).getKks(), connectionTable.getItems().get(r).getPage_esg());

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
            for (TableColumn p : connectionTable.getColumns()){
                //System.out.println(p.getText());
                clipboardString.append(p.getText());
                if (colN < connectionTable.getColumns().size()-1){
                    clipboardString.append("\t");
                }
                colN++;
            }
            clipboardString.append('\n');

            ObservableList<TablePosition> posList = connectionTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                int r = p.getRow();
                int c = p.getColumn();
                //System.out.println("row " + r + " col " + c);

                clipboardString.append(connectionTable.getItems().get(r).getCabinet());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getKks());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getPage_no());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getPage_esg());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getPage_nobi());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getValue());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getAddr());
                clipboardString.append('\t');
                clipboardString.append(connectionTable.getItems().get(r).getHet());
                clipboardString.append('\n');

            }

            fileWriter.write(String.valueOf(clipboardString));
            fileWriter.close();
        }
        catch (IOException e){

        }
    }


    public void setSimtableController(simtableController simtableController) {
        this.simtableController = simtableController;
    }

}
