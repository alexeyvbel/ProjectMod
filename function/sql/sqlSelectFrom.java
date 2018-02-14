package function.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.ResultSet.CONCUR_READ_ONLY;

/**
 * Created by BELYANIN on 11.01.2018.
 */
public class sqlSelectFrom {

    public ResultSet resultSet;
    public Connection connection;
    //-----------Запрос данных из таблицы
    public sqlSelectFrom(String schemaname, String tablename) throws SQLException {
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT * FROM " + schemaname + "." + tablename);
    }

}
