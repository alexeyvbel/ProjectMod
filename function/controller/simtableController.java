package function.controller;

import function.Table.simulationTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by BELYANIN on 06.02.2018.
 */
public class simtableController {

    @FXML
    private TableView<simulationTable> simulationTable;

    @FXML
    private TableColumn<simulationTable, String> kkscolumn;

    @FXML
    private TableColumn<simulationTable, String> markercolumn;

    @FXML
    private TableColumn<simulationTable, String> commentcolumn;

    @FXML
    private Button createSimulationFile;

    @FXML
    private Button clearSimulationTable;

    private showAlert showAlert = new showAlert();


    private static ObservableList<simulationTable> data;

    private FXMLLoader fxmlLoaderConnectionTable;
    private connectiontableController connectiontableController;

    public void initialize(){
        //fxmlLoaderConnectionTable = new FXMLLoader();
        //fxmlLoaderConnectionTable.setLocation(getClass().getResource("/menu/connectiontable.fxml"));
        //connectiontableController = fxmlLoaderConnectionTable.getController();
        createSimulationFile.setDisable(true);
        clearSimulationTable.setDisable(true);
        simulationTable.getSelectionModel().setCellSelectionEnabled(false);
        simulationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void createSimulationFileAction(ActionEvent event) throws IOException {
        FileChooser fileChooserSaveAll = new FileChooser();
        File file = fileChooserSaveAll.showSaveDialog(new Stage());
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        //fileChooserSaveAll.getExtensionFilters().add(extFilter);
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);

        StringBuilder clipboardString = new StringBuilder();

        simulationTable.getSelectionModel().selectAll();
        ObservableList<TablePosition> posList = simulationTable.getSelectionModel().getSelectedCells();
        int old_r = -1;
        //Считываем данные из таблицы
        for (TablePosition p : posList) {

            int r = p.getRow();
            int c = p.getColumn();
            //System.out.println("row " + r + " col " + c);
            String[] strmarker = simulationTable.getItems().get(r).getMarker().split("_");
            String namekks = simulationTable.getItems().get(r).getKks();
            //strmarker[0] = abonent
            //strmarker[1] = markerk
            //10KBC10CF001

            String[] strtype = strmarker[1].split(",",2);

            if (strtype[0].contains("EAS") || strtype[0].contains("ES")) {

                clipboardString.append(strmarker[0] + ":::::::" + strmarker[1] + ":::" + namekks.substring(2,7) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":::::::ES" +  strtype[1].substring(2,strtype[1].length()-1) + "0:::" + namekks.substring(7,namekks.length()) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');

            } else if (strtype[0].contains("AAS") || strtype[0].contains("AS")) {

                clipboardString.append(strmarker[0] + ":::::::" + strmarker[1] + ":::" + namekks.substring(2,7) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append("::::::::::" + namekks.substring(7,namekks.length()) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');

            } else if (strtype[0].contains("M")){

                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,M:AZS::B,76:::" + namekks.substring(2,7) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,M:RMA::B,55:::" + namekks.substring(7,namekks.length()) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,M:RME::B,58::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');

            }
            else if (strtype[0].contains("S")){

                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,M:AZS::B,76:::" + namekks.substring(2,7) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:WEZU::B,55:::" + namekks.substring(7,namekks.length()) + ":1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:WEAF::B,58::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:WENZU::B,56::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:WENAF::B,57::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:DENAF::B,59::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');
                clipboardString.append(strmarker[0] + ":" + strtype[1].replace(",",":") + ":ESG,0,S:DENZU::B,54::::1:1:0:0:1:0:1:1:0:0:1::0:0:");
                clipboardString.append('\n');

            }


        }

        fileWriter.write(String.valueOf(clipboardString));
        fileWriter.close();
        showAlert.infromationAlert("Файл",null,"Формирование файла имитации выполненно!");

    }

    @FXML
    public void clearSimulationTableAction(ActionEvent event){
        simulationTable.getItems().clear();
        createSimulationFile.setDisable(true);
        clearSimulationTable.setDisable(true);

    }

    public void addDatatoTable (String kks, String marker){

        //System.out.println("Добавляем данные в таблицу имитаций");
        //simulationTable.setEditable(true);
        createSimulationFile.setDisable(false);
        clearSimulationTable.setDisable(false);
        kkscolumn.setCellValueFactory(new PropertyValueFactory<simulationTable, String>("kks"));
        markercolumn.setCellValueFactory(new PropertyValueFactory<simulationTable, String>("marker"));
        commentcolumn.setCellValueFactory(new PropertyValueFactory<simulationTable, String>("comment"));

        data = FXCollections.observableArrayList();
        data.add(new simulationTable(kks, marker,""));
        //simulationTable.setItems(null);
        simulationTable.getItems().addAll(data);
    }


    public void setSimulationTable(TableView<simulationTable> simulationTable) {
        this.simulationTable = simulationTable;
    }

    public TableView<simulationTable> getSimulationTable() {
        return simulationTable;
    }
}
