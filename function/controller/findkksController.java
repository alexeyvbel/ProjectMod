package function.controller;


import function.sql.DBSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;


public class findkksController {

    private DBSql dbselect = new DBSql("localhost", "5439", "postgres" , "postgres", "postgres");
    private ObservableList<String> listFindkks = FXCollections.observableArrayList();
    private ObservableList  data;
    @FXML
    private TextField textfieldFindKks;

    @FXML
    private ListView<String> listViewFindKks;

    @FXML
    private AnchorPane paneSeaView;

    @FXML
    private CheckBox checkBoxInSignal;

    @FXML
    private CheckBox checkBoxOutSignal;

    @FXML
    private CheckBox checkBoxInfluSignal;

    @FXML
    private CheckBox checkBoxConnectSignal;

    @FXML
    private CheckBox checkBoxInSignalTSODU;

    @FXML
    private CheckBox checkBoxOutSignalTSODU;

    boolean[] valueCheckBox = new boolean[4];


    private TableView<ObservableList> tableSeaView;

    private showAlert showAlert = new showAlert();

    private simtableController simtableController;


    //----Начальное обновление опций поиск
    public void initialize() throws SQLException, ClassNotFoundException {
        checkBoxInSignal.setSelected(true);
        checkBoxOutSignal.setSelected(true);
        checkBoxConnectSignal.setSelected(true);

        loadFindkks();

    }

    public findkksController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    //Обновить окно поиска сигналов
    public void updateListViewFindKks (ActionEvent event) throws SQLException, ClassNotFoundException{
        loadFindkks();
    }

    public void loadFindkks() throws SQLException, ClassNotFoundException {
        if(dbselect.SQLCountRow("dbselect","findkks") != 0) {
            listFindkks.removeAll();
            listViewFindKks.getItems().clear();
            dbselect.SQLSelectFrom("*","dbselect","findkks");
            while (dbselect.resultSet.next()){
                listFindkks.add(dbselect.resultSet.getString(1));
                listViewFindKks.setItems(listFindkks);
            }

        }
    }

    @FXML
    //Отчистить список и таблицу для поиска
    public void clearFieldSignalButton (ActionEvent event) throws SQLException {
        listViewFindKks.getItems().clear();
        dbselect.SQLDeleteFrom("dbselect","findkks");

    }

    @FXML
    //Кнопка добавления сигналов для поиска
    public void addSignalButton (ActionEvent event) throws IOException, SQLException {
        dataAdd();
    }


    @FXML
    public void enterPressed (KeyEvent keyEvent) throws IOException, SQLException {
        if(keyEvent.getCode() == KeyCode.ENTER) dataAdd();
    }

    public void dataAdd() throws IOException, SQLException {

        if(dbselect.SQLCountRow("dbselect","pages", "a.page_kks LIKE '%" + textfieldFindKks.getText().toUpperCase() + "%'") != 0) {

            dbselect.SQlAddDataToFindkks(textfieldFindKks.getText().toUpperCase());
            listFindkks.removeAll();
            listViewFindKks.getItems().clear();
            dbselect.SQLSelectFrom("*","dbselect","findkks");

            while (dbselect.resultSet.next()){
                listFindkks.add(dbselect.resultSet.getString(1));
            }

            listViewFindKks.setItems(listFindkks);

        } else {
            showAlert.infromationAlert("Опции поиска",null,"Такой сигнал в GET-проекте отсутствует");
        }
    }

    @FXML
    //Поиск информации в соотвествии с выставленными опциями
    public void FindConnectonButton (ActionEvent event) throws SQLException {
        //System.out.println("Запуск поиска ккс");
        dbselect.SQLFindKks();
        //System.out.println("Поиск связей завершен");
    }

    @FXML
    //Просмотр найденных связей
    public void seaConnectionButton (ActionEvent event) throws IOException, SQLException, Exception{

        Stage stage = new Stage();
        StackPane stackPane = new StackPane();
        HBox hBox;
        VBox vBox;
        Scene scene;
        TableView inputsignalPane;
        TableView outputsignalPane;
        TableView connectionPane;
        FXMLLoader fxmlLoader;


        //Создаем массив boolean для переключалок
        valueCheckBox[0] = checkBoxInSignal.isSelected();
        valueCheckBox[1] = checkBoxOutSignal.isSelected();
        valueCheckBox[2] = checkBoxInfluSignal.isSelected();
        valueCheckBox[3] = checkBoxConnectSignal.isSelected();

        fxmlLoader = new FXMLLoader(getClass().getResource("/menu/inputsignal.fxml"));
        inputsignalPane = fxmlLoader.load();

        outputsignalPane = FXMLLoader.load(getClass().getResource("/menu/outputsignal.fxml"));

        fxmlLoader = new FXMLLoader(getClass().getResource("/menu/connectiontable.fxml"));
        connectionPane  = fxmlLoader.load();
        connectiontableController connectiontableController = fxmlLoader.<connectiontableController>getController();
        connectiontableController.setValcheckbox(Arrays.toString(valueCheckBox));
        connectiontableController.setSimtableController(simtableController);
        int flag = 1;
        switch (Arrays.toString(valueCheckBox)) {
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
            stage.setTitle(textfieldFindKks.getText());
            scene = new Scene(stackPane);
            stage.setScene(scene);
            stage.show();
        }



    }


    public void setSimtableController(simtableController simtableController) {
        this.simtableController = simtableController;
    }
}


/* ХЛАМ
        valueCheckBox[0] = checkBoxInSignal.isSelected();
        valueCheckBox[1] = checkBoxOutSignal.isSelected();
        valueCheckBox[2] = checkBoxInfluSignal.isSelected();
        valueCheckBox[3] = checkBoxConnectSignal.isSelected();

        int count = (valueCheckBox[0]?1:0)+(valueCheckBox[1]?1:0)+(valueCheckBox[3]?1:0);
        int indexTable = 100*(valueCheckBox[0]?1:0)+10*(valueCheckBox[1]?1:0)+(valueCheckBox[3]?1:0);
        System.out.println(count);
        StackPane root = new StackPane ();




        //Цикл для построения трех таблиц по вертикали
        System.out.println("Количество таблиц " + count);
        //for (int intdexTablei = 1; intdexTablei <= count; intdexTablei++) {
            tableSeaView = new TableView();
            //tableSeaView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            //AnchorPane root = FXMLLoader.load(getClass().getResource("menuSeaView.fxml"));


            //Запросы для просмотра данных
            //dbselect.SQLSelectFrom("kksin, page_kks, page_no, block_type, block_name, fbport_techname, fbport_marker ","dbselect","temp","fbport_marker IS NOT NULL");
            //dbselect.SQLResultSetMetaData("kksin, page_kks, page_no, block_type, block_name, fbport_techname, fbport_marker ","dbselect","temp","fbport_marker IS NOT NULL");

        dbselect.SQLSelectFrom("kks, page_no ","dbselect","kksoption");
        dbselect.SQLResultSetMetaData("kks, page_no ","dbselect","kksoption");



        if (indexTable >= 100){
                dbselect.SQLSelectFrom("pageleft, pageleftno, signalcodein, telegramin, abonent_id, page_kks, page_no ","dbselect","inputsignal1");
                dbselect.SQLResultSetMetaData("pageleft, pageleftno, signalcodein, telegramin, abonent_id, page_kks, page_no ","dbselect","inputsignal1");
                indexTable = indexTable - 100;
                System.out.println(indexTable);
            }
            else if (indexTable >= 10){
                dbselect.SQLSelectFrom("abonent_id, page_kks, page_no, signalcode, telegram, pageright, pagerightno ","dbselect","outputsignal");
                dbselect.SQLResultSetMetaData("abonent_id, page_kks, page_no, signalcode, telegram, pageright, pagerightno ","dbselect","outputsignal");
                indexTable = indexTable - 10;
                System.out.println(indexTable);
            }
            else if (indexTable >= 1){
                dbselect.SQLSelectFrom("cabinet, kks, page_no, page_esg, page_nobi, value, addr","dbselect ","connectiontable");
                dbselect.SQLResultSetMetaData("cabinet, kks, page_no, page_esg, page_nobi, value, addr","dbselect ","connectiontable");
                indexTable = indexTable - 1;
                System.out.println(indexTable);
            }


        //System.out.println(dbselect.resultSet.getMetaData().getColumnCount());
            for(int i = 0 ; i < dbselect.resultSet.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn tableColumn = new TableColumn(dbselect.resultSet.getMetaData().getColumnName(i+1));

                //System.out.println(j);
                tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });


                //System.out.println("Добавляем столбцы " + dbselect.resultSet.getMetaData().getColumnName(i+1));
                tableSeaView.getColumns().addAll(tableColumn);

                //System.out.println("Column ["+i+"] ");
            }
        System.out.println(tableSeaView.getColumns().get(0).getText());
            data = FXCollections.observableArrayList();
            while (dbselect.resultSet.next()){
                Vector<String>  row = new Vector<String>();

                ObservableList<String> list = FXCollections.observableArrayList();


                for(int col = 1; col <= dbselect.resultSetMetaData.getColumnCount(); col++) {
                    //System.out.println("Добавляем данные в таблицу");
                    int type = dbselect.resultSetMetaData.getColumnType(col);

                    switch(type) {

                        case Types.INTEGER :
                            list.add(new Integer(dbselect.resultSet.getInt(col)).toString());
                            //System.out.println(new Integer(dbselect.resultSet.getInt(col)).toString());
                            break;

                        case Types.VARCHAR :
                            //System.out.println(dbselect.resultSet.getString(col));
                            list.add(dbselect.resultSet.getString(col));
                            break;

                        default :
                                /*В этой моей тестовой таблице всего два типа полей: целое и строка.
                                 *Соответственно если в таблице/запросе типов больше, то этот switch
                                 *нужно расширить соответствующими типами, а сейчас, в случае если мы
                                 *встретим неизвестный коду тип поля, я для наглядности бросаю исключение.
                                 * */
                /*            throw new Exception("Неподдерживаемый тип");
                    }

                }

                data.add(list);


            }
        System.out.println(data);
        tableSeaView.getItems().addAll(data);

        System.out.println(tableSeaView.getItems());
        //}







/*
        //Контекстное меню по нажатию правой кнопки
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemSaveAll = new MenuItem("Сохранить все в фвйл");

        itemSaveAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    FileChooser fileChooserSaveAll = new FileChooser();
                    File file = fileChooserSaveAll.showSaveDialog(stage);
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                    fileChooserSaveAll.getExtensionFilters().add(extFilter);
                    FileWriter fileWriter = null;
                    fileWriter = new FileWriter(file);

                    StringBuilder clipboardString = new StringBuilder();
                    int colN = 0;
                    //Считываем намименование столбцов в таблице
                    for (TableColumn p : tableSeaView.getColumns()){
                        //System.out.println(p.getText());
                        clipboardString.append(p.getText());
                        if (colN < tableSeaView.getColumns().size()-1){
                            clipboardString.append("\t");
                        }
                        colN++;
                    }
                    clipboardString.append('\n');

                    tableSeaView.getSelectionModel().setCellSelectionEnabled(true);
                    tableSeaView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    tableSeaView.getSelectionModel().selectAll();
                    ObservableList<TablePosition> posList = tableSeaView.getSelectionModel().getSelectedCells();
                    int old_r = -1;
                    //Считываем данные из таблицы
                    for (TablePosition p : posList) {
                        int r = p.getRow();
                        int c = p.getColumn();

                        Object cell = tableSeaView.getColumns().get(c).getCellData(r);
                        if (cell == null)
                            cell = "";
                        if (old_r == r)
                            clipboardString.append('\t');
                        else if (old_r != -1)
                            clipboardString.append('\n');
                        clipboardString.append(cell);
                        old_r = r;

                    }

                    fileWriter.write(String.valueOf(clipboardString));
                    fileWriter.close();
                }
                catch (IOException e){

                }
            }
        });

        MenuItem itemSave = new MenuItem("Сохранить в файл");
        MenuItem itemAddImitaion = new MenuItem("Имитация");
        contextMenu.getItems().addAll(itemSaveAll, itemSave, itemAddImitaion);

        tableSeaView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(root,event.getScreenX(), event.getScreenY());
            }
        });


        System.out.println("Выводим на экран");
        Scene scene = new Scene(root,400,400);
        root.getChildren().add(tableSeaView);
        stage.setScene(scene);
        stage.show();
     */

