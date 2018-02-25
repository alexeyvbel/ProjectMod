package function.controller;

import function.Table.connectionTable;
import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
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

    private String kksfind;

    private showAlert showAlert = new showAlert();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public connectiontableController() throws SQLException, ClassNotFoundException {

    }



    public void setoption(String valuecheckbox, String kksfind) throws SQLException, ClassNotFoundException {

        //contextMenu.getItems().addAll(itemSaveAll, itemSave, itemAddImitaion);


        this.kksfind = kksfind;
        this.valcheckbox = valuecheckbox;


        connectionTable.getSelectionModel().setCellSelectionEnabled(false);
        connectionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Задаем параметры для столбцов таблицы
        cabinet.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        kks.setCellValueFactory(new PropertyValueFactory<>("kks"));
        page_no.setCellValueFactory(new PropertyValueFactory<>("page_no"));
        page_esg.setCellValueFactory(new PropertyValueFactory<>("page_esg"));
        page_nobi.setCellValueFactory(new PropertyValueFactory<>("page_nobi"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        addr.setCellValueFactory(new PropertyValueFactory<>("addr"));
        het.setCellValueFactory(new PropertyValueFactory<>("het"));



        try {
        dbselect.SQLConnectionTable(valcheckbox, kksfind);
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

    @FXML
    public void OnMouseClicked(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

        if(event.getClickCount()>1){
            ObservableList<TablePosition> posList = connectionTable.getSelectionModel().getSelectedCells();
            int old_r = -1;
            int c = -1;
            int r = -1;
            //Считываем данные из таблицы
            for (TablePosition p : posList) {

                r = p.getRow();
                c = p.getColumn();
                //System.out.println("row " + r + " col " + c);

                switch (c){
                    case 1:
                        kksfind = connectionTable.getItems().get(c).getKks();
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


    public void setSimtableController(simtableController simtableController) {
        this.simtableController = simtableController;
    }

}
