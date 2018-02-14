package function.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by BELYANIN on 11.01.2018.
 */
public class dbConnection {

    private String ipname = "localhost";
    private String nameport = "5439";
    private String dbname = "postgres";
    private String user = "postgres";
    private String password = "postgres";
    public Connection connection;

    //------------Установка соединения с БД
    public dbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager
                .getConnection("jdbc:postgresql://" + ipname + ":" + nameport + "/" + dbname,
                        "" + user + "", "" + password + "");
    }

    public dbConnection (String ipname, String nameport, String dbname, String user, String password) throws SQLException, ClassNotFoundException {
        this.ipname = ipname;
        this.nameport = nameport;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        Class.forName("org.postgresql.Driver");
        connection = DriverManager
                .getConnection("jdbc:postgresql://" + ipname + ":" + nameport + "/" + dbname,
                        "" + user + "", "" + password + "");
    }


}
