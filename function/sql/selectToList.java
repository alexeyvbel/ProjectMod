package function.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by BELYANIN on 29.01.2018.
 */
public class selectToList {

    public ObservableList observableList = FXCollections.observableArrayList();

    public selectToList(Connection connection,  String tableName, String fieldName) throws SQLException {
        ResultSet resultSet =  connection.createStatement().executeQuery("SELECT DISTINCT " + fieldName + " FROM " + tableName);

        while (resultSet.next()) {
            observableList.add(resultSet.getString(1));
        }
    }


    public selectToList(Connection connection, String tableName, String fieldName, String fieldWhere, String stringWhere) throws SQLException {
        ResultSet resultSet =  connection.createStatement().executeQuery("SELECT DISTINCT " + fieldName + " FROM " + tableName + " WHERE " + fieldWhere + " = " + "'" + stringWhere + "'");
        while (resultSet.next()) {
            observableList.add(resultSet.getString(1));
        }

    }


}
