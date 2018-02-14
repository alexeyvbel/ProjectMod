package function.controller;


import function.sql.dbConnection;
import function.sql.DBSql;
import function.sql.selectToList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

//Обработчик событий для сцены работы с селектом
public class createselectController  {

    @FXML
    private ListView<String> listViewSchemas;

    @FXML
    private ListView<String> listViewTables;

    @FXML
    private ProgressBar progressBarIndex = new ProgressBar();

    @FXML
    private Label labelProgress;

    private Task taskSelect;

    @FXML
    private TextField dbName;

    @FXML
    private TextField dbPort;

    @FXML
    private TextField dbLogin;

    @FXML
    private TextField dbPassw;

    @FXML TextField dbHost;

    private ObservableList projectNameList = FXCollections.observableArrayList();

    @FXML
    private ComboBox projectName = new ComboBox(projectNameList);

    @FXML
    private TextField addSchemaName;

    public dbConnection dbselect1;

    private showAlert showAlert = new showAlert();

    double count;
    double index;

    //----Начальное обновление поля наименование проекта
    public void initialize() throws ClassNotFoundException {

        dbName.setText("getdb");
        dbPort.setText("5439");
        dbLogin.setText("postgres");
        dbPassw.setText("postgres");
        dbHost.setText("localhost");

        try {
            dbselect1 = new dbConnection("localhost", "5439", "postgres", "postgres", "postgres");
            projectName.getItems().addAll(new selectToList(dbselect1.connection, "dbselect.projects_table", "project_name").observableList);

        } catch (SQLException e) {
            showAlert.warningAlert("Сборка селекта",null,"Отсутствует подключение к БД!");
            e.printStackTrace();
        }



        listViewTables.setItems(new DBParametrs().itemsTables);
        listViewTables.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    //------Действие при выборе сохраненного проекта
    public void projectNameView (ActionEvent event) throws ClassNotFoundException, SQLException {

        listViewSchemas.setItems(new selectToList(dbselect1.connection, "dbselect.projects_table", "table_name","project_name", projectName.getSelectionModel().getSelectedItem().toString()).observableList);
        listViewSchemas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }



    @FXML
    //Действие при нажатии кнопки Собрать селект
    public void createSelect(ActionEvent event)  {
        taskSelect = taskCreatSelect();
        progressBarIndex.progressProperty().bind(taskSelect.progressProperty());
        labelProgress.textProperty().bind(taskSelect.messageProperty());
        new Thread(taskSelect).start();


    }

    @FXML
    //Действие при нажатии кнопки Загрузить селект
    public void loadDataSelect(ActionEvent event) throws ClassNotFoundException, SQLException {

    }

//------Задача по сброке селекта
    public Task taskCreatSelect() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    //System.out.println("Запуск сборки селекта");
                    DBSql dbget = new DBSql(dbHost.getText(), dbPort.getText(), dbName.getText(), dbLogin.getText(), dbPassw.getText());
                    DBSql dbselect = new DBSql("localhost", "5439", "postgres" , "postgres", "postgres");

                    //Создание схемы в бд
                    //System.out.println("Создание схемы в бд");
                    String schemaname = "dbselect";
                    dbselect.SQLCreateSchema(schemaname);


                    String tableName;
                    String schemanameGet;
                    //Пробегаем по всем таблицам
                    for (Object objectTables : listViewTables.getItems()) {
                        //Удаление таблицы в бд
                        //System.out.println("Удаление таблицы в бд");
                        tableName = objectTables.toString();

                        dbselect.SQLDeleteTable(schemaname, tableName);
                        //Пробегаем по всем схемам
                        int flag = 0;
                        for (Object objectSchemas : listViewSchemas.getSelectionModel().getSelectedItems()) {
                            schemanameGet = objectSchemas.toString();

                            dbget.SQLResultSetMetaData(schemanameGet, tableName);
                            //Создание таблиц в dbselect
                            if (flag == 0) {
                                flag = 1;
                                dbselect.SQLCreateTable(schemaname, tableName, dbget.resultSetMetaData.getColumnName(1), dbget.resultSetMetaData.getColumnTypeName(1));
                                for (int i = 2; i <= dbget.resultSetMetaData.getColumnCount(); i++) {
                                    dbselect.SQLAddDataTable(schemaname, tableName, dbget.resultSetMetaData.getColumnName(i), dbget.resultSetMetaData.getColumnTypeName(i));
                                }
                            }

                            //Запрос данных из GET
                            dbget.SQLSelectFrom(schemanameGet, tableName);

                            String fieldname = "";
                            String strvalues = "";
                            int i;
                            for (i = 1; i < dbget.resultSetMetaData.getColumnCount(); i++) {
                                if(tableName.equals("Outfbport") && dbget.resultSetMetaData.getColumnName(i).equals("fbport_description")) {
                                    i++;
                                }
                                else {
                                    fieldname += dbget.resultSetMetaData.getColumnName(i) + ", ";
                                    strvalues += "?, ";
                                }
                            }
                            fieldname += dbget.resultSetMetaData.getColumnName(i) + "";
                            strvalues += "?";
                            dbselect.SQLtmpUpdate(schemaname, tableName, fieldname, strvalues);
                            //dbget.resultSet.setFetchSize(1000);
                            //dbget.connection.setAutoCommit(false);

                            dbget.resultSet.last();
                            count = dbget.resultSet.getRow();
                            dbget.resultSet.beforeFirst();

                            //progressBarIndex.progressProperty().bind(taskCreateSelect.progressProperty());
                            //labelProgress.textProperty().bind(taskCreateSelect.messageProperty());

                            //new Thread(taskCreateSelect).start();

                            while (dbget.resultSet.next()) {
                                index = dbget.resultSet.getRow();
                                updateProgress(index, count);
                                updateMessage(schemanameGet + ' ' + tableName);
                                for (int j = 1, k=1; j <= dbget.resultSetMetaData.getColumnCount(); j++) {
                                    if(tableName.equals("Outfbport") && dbget.resultSetMetaData.getColumnName(j).equals("fbport_description")) {
                                        j++;
                                    }
                                    else {
                                        dbselect.SQLSwitchType(dbget.resultSetMetaData.getColumnTypeName(j), k, dbget.resultSet);
                                        k++;
                                    }
                                }
                                dbselect.SQLUpdateData();
                            }


                        }
                    }

                    dbselect.SQLDeleteFrom("dbselect","dbconnection");
                    dbselect.SQLCreatedbconnection();
                    dbselect.SQLDeleteFrom("dbselect","kksoption");
                    dbselect.SQLCreateKksOption();
                    showAlert.infromationAlert("Сборка селекта",null,"Сборка селекта завершена!");

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                return true;
            }
        };
    }
}


