package function.sql;

import java.sql.*;

import static java.sql.ResultSet.CONCUR_READ_ONLY;

/**
 * Created by BELYANIN on 11.01.2018.
 */
public class DBSql {

    public Connection connection;
    public Statement statement;
    public ResultSetMetaData resultSetMetaData;
    public ResultSet resultSet;
    public PreparedStatement preparedStatement;

    //------------Установка соединения с БД
    public DBSql(String host, String port, String dbname, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager
                .getConnection("jdbc:postgresql://" + host + ":" + port + "/" + dbname,
                        "" + user + "", "" + password + "");
    }

    //---------SQL-запрос создания схемы в бд
    public void SQLCreateSchema(String schemaname) throws SQLException {
        connection.createStatement().executeUpdate("CREATE SCHEMA IF NOT EXISTS " + schemaname);
    }

    //----------SQL-запрос удаления таблицы в бд
    public void SQLDeleteTable(String schemaname, String tablename) throws SQLException{
        connection.createStatement().executeUpdate("DROP TABLE IF EXISTS " + schemaname + "." + tablename);
    }

    //-----------Запрос данных из таблицы
    public void SQLResultSetMetaData(String schemaname, String tablename) throws SQLException{
        resultSetMetaData = connection.prepareStatement("SELECT * FROM " + schemaname + "." + tablename).executeQuery().getMetaData();
    }

    //-----------Запрос данных из таблицы
    public void SQLResultSetMetaData(String field, String schemaname, String tablename) throws SQLException{
        resultSetMetaData = connection.prepareStatement("SELECT " + field + " FROM " + schemaname + "." + tablename).executeQuery().getMetaData();
    }

    //-----------Запрос данных из таблицы
    public void SQLResultSetMetaData(String field, String schemaname, String tablename, String where) throws SQLException{
        resultSetMetaData = connection.prepareStatement("SELECT " + field + " FROM " + schemaname + "." + tablename + " WHERE " + where).executeQuery().getMetaData();
    }



    //-----------Создание таблицы в бд c одним полем и типом данных
    public void SQLCreateTable(String schemaname, String tablename, String ColumnName, String ColumnTypeName) throws SQLException{
        connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + schemaname + "." + tablename + " " + "(" + ColumnName + " " + ColumnTypeName + ")");
    }

    //-----------Создание таблицы в бд c несколькими полями и типом данных
    public void SQLCreateTable(String schemaname, String tablename, String Column) throws SQLException{
        connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + schemaname + "." + tablename + " " + "(" + Column +  ")");
    }

    //-----------Добавление полей в таблицу
    public void SQLAddDataTable(String schemaname, String tablename, String fieldname, String typename) throws SQLException{
        //con2.createStatement().executeUpdate("ALTER TABLE dbselect." + tablesn + " ADD " + rsmd.getColumnName(i) + " " + rsmd.getColumnTypeName(i));
        connection.createStatement().executeUpdate("ALTER TABLE " + schemaname + "." + tablename + " ADD " + fieldname + " " + typename);
    }

    //-----------Запрос данных из таблицы
    public void SQLSelectFrom(String schemaname, String tablename) throws SQLException{
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT * FROM " + schemaname + "." + tablename);
    }

    //-----------Запрос данных из таблицы
    public void SQLSelectFrom(String field, String schemaname, String tablename) throws SQLException{
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT " + field + " FROM " + schemaname + "." + tablename);
    }

    //-----------Запрос данных из таблицы
    public void SQLSelectFrom(String field, String schemaname, String tablename, String where) throws SQLException{
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT " + field + " FROM " + schemaname + "." + tablename + " WHERE " + where);
    }

    //-----------Удаление данных из таблицы
    public void SQLDeleteFrom(String schemaname, String tablename) throws SQLException{
        connection.prepareStatement("DELETE FROM " + schemaname + "." + tablename).executeUpdate();
    }

    //-----------Подготовка запроса для обновления данных
    public void SQLtmpUpdate(String schemaname, String tablename, String fieldname, String strvalues) throws SQLException{
        preparedStatement = connection.prepareStatement( "INSERT INTO " + schemaname + "." + tablename + " (" + fieldname + ") VALUES (" + strvalues + ")");
    }

    //----------- Подсчет количества строк в таблице
    public int SQLCountRow(String schemaname, String tablename) throws SQLException{
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT COUNT (a.*) FROM " + schemaname + "." + tablename + " AS a");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int SQLCountRow(String schemaname, String tablename, String wherestring) throws SQLException{
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT COUNT (a.*) FROM " + schemaname + "." + tablename + " AS a" + " WHERE " + wherestring);
        resultSet.next();
        return resultSet.getInt(1);
    }


    //Установка данных вместо ?
    public void SQLSwitchType(String type, int i, ResultSet resultSet1 ) throws SQLException {
        switch (type) {
            case "timestamp":
                preparedStatement.setTimestamp(i, resultSet1.getTimestamp(i));
                break;
            case "text":
                preparedStatement.setString(i, resultSet1.getString(i));
                break;
            case "int4":
                preparedStatement.setInt(i, resultSet1.getInt(i));
                break;
            case "varchar":
                preparedStatement.setString(i, resultSet1.getString(i));
                break;
            case "float8":
                preparedStatement.setDouble(i, resultSet1.getDouble(i));
                break;
            case "date":
                preparedStatement.setDate(i, resultSet1.getDate(i));
                break;
            case "bool":
                preparedStatement.setBoolean(i, resultSet1.getBoolean(i));
                break;

        }
    }

    //------------Обновление данных
    public void SQLUpdateData() throws SQLException{
        preparedStatement.executeUpdate();
    }

    //--------------Добавление данных в findkks
    public void SQlAddDataToFindkks(String namefindkks) throws SQLException {
        connection.createStatement().executeUpdate("INSERT INTO dbselect.findkks (kks) \n" +
                " SELECT DISTINCT a.page_kks\n" +
                " FROM dbselect.pages AS a\n" +
                " WHERE a.page_kks LIKE" + "'%" + namefindkks + "%'");
    }

    //------------Поиск связей
    public void SQLFindKks () throws SQLException {
        //connection.createStatement().executeUpdate("DROP TABLE IF EXISTS dbselect.temp");
        System.out.println("Темп удален");
        /*connection.createStatement().executeUpdate("CREATE TABLE dbselect.temp \n " +
                " (pageleft VARCHAR, pageleftno VARCHAR, signalcodein VARCHAR, abonent_id INTEGER, page_id INTEGER, \n" +
                " page_kks VARCHAR, page_no VARCHAR, block_type VARCHAR, signalcode VARCHAR, pageright VARCHAR, pagerightno VARCHAR, block_name VARCHAR, \n" +
                " fbport_techname VARCHAR, fbport_marker VARCHAR, block_id INTEGER, port_id INTEGER, connector_id INTEGER, connector_port_id INTEGER, kksin VARCHAR, \n" +
                " level1 INTEGER, level2 INTEGER, pathblock INTEGER[], cycle BOOLEAN, pathpages INTEGER[] )");
        System.out.println("Темп создан");
        */
        connection.prepareStatement("DELETE FROM dbselect.temp").executeUpdate();
        connection.createStatement().executeUpdate("INSERT INTO dbselect.temp (pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, kksin, sigin, level1, level2, pathblock, cycle, pathpages)\n" +
                " SELECT DISTINCT pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, (pageleft) AS kksin, signalcodein as sigin, 1 AS level1, 1 AS level2, ARRAY[block_id], FALSE, ARRAY[page_id] \n" +
                " FROM dbselect.dbconnection, dbselect.findkks \n" +
                " WHERE pageleft = kks AND pageleft <> page_kks");
        System.out.println("Темп подготовлен");
        int flag = 1;
        int i = 1;
        while (flag == 1) {
            System.out.println(i);
            connection.createStatement().executeUpdate("WITH RECURSIVE findblock(pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, kksin, level1, level2, pathblock, cycle, pathpages, sigin) AS (\n" +
                    "    SELECT pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, kksin, level1, level2, pathblock, cycle, pathpages, sigin\n" +
                    "    FROM dbselect.temp\n" +
                    "    WHERE pageleft <> '' AND level1 = " + i + " AND level2 < 2\n" +
                    "  UNION ALL \n" +
                    "  SELECT b.pageleft, b.pageleftno, b.signalcodein, b.abonent_id, b.page_id, b.page_kks, b.page_no, b.block_type, b.signalcode, b.pageright, b.pagerightno, b.block_name, b.fbport_techname, b.fbport_marker, b.block_id, b.port_id, b.connector_id, b.connector_port_id,\n" +
                    "    CASE WHEN b.pageleft IS NULL THEN a.kksin\n" +
                    "         WHEN b.pageleft <> '' THEN (a.kksin) END, \n" +
                    " a.level1,\n" +
                    "   CASE WHEN b.pageright <> b.page_kks THEN (a.level2) ELSE a.level2 END, \n" +
                    "   (a.pathblock || b.block_id), b.block_id = ANY(a.pathblock), a.pathpages,\n" +
                    "           CASE WHEN b.pageleft IS NULL THEN a.sigin \n" +
                    "                WHEN b.pageleft <> '' THEN (a.sigin) END \n" +
                    "  FROM findblock a\n" +
                    "    INNER JOIN dbselect.dbconnection b\n" +
                    "      ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.connector_id = b.block_id\n" +
                    " WHERE a.cycle IS FALSE \n" +
                    " )\n" +
                    " INSERT INTO dbselect.temp\n" +
                    " SELECT pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, kksin, level1, \n" +
                    " CASE WHEN pageright <> page_kks THEN (level2 + 1) ELSE level2 END, pathblock, cycle, pathpages, sigin \n" +
                    " FROM findblock\n" +
                    " WHERE (block_name LIKE '%esg%' OR pageright <> '') AND cycle IS FALSE");
            System.out.println("Запуск рекурсии");

            connection.createStatement().executeUpdate("INSERT INTO dbselect.temp (pageleft, pageleftno, signalcodein, abonent_id, page_id, page_kks, page_no, block_type, signalcode, pageright, pagerightno, block_name, fbport_techname, fbport_marker, block_id, port_id, connector_id, connector_port_id, kksin, sigin, level1, level2, pathblock, cycle, pathpages) \n" +
                    " SELECT b.pageleft, b.pageleftno, b.signalcodein, b.abonent_id, b.page_id, b.page_kks, b.page_no, b.block_type, b.signalcode, b.pageright, b.pagerightno, b.block_name, b.fbport_techname, b.fbport_marker, b.block_id, b.port_id, b.connector_id, b.connector_port_id, a.kksin, a.sigin,  " + (i + 1) + " as level1, \n" +
                    " CASE WHEN b.pageright <> b.page_kks THEN (a.level2+1) ELSE a.level2 END, a.pathblock, a.cycle, a.pathpages || b.page_id \n" +
                    " FROM dbselect.temp AS a\n " +
                    " INNER JOIN dbselect.dbconnection AS b ON a.signalcode = b.signalcodein AND a.pageright = b.page_kks AND a.page_kks = b.pageleft\n" +
                    "   WHERE a.level1 = " + i + " AND a.pageright <> '' AND a.level2 < 2 AND (b.page_id = ANY(a.pathpages)) IS FALSE ");
            System.out.println("Запрос после рекурсии");

            ResultSet resultSettemp = connection.createStatement().executeQuery("SELECT COUNT(a.*) AS count FROM dbselect.temp AS a WHERE a.level1 = " + (i + 1) + " AND a.level2 < 2");
            resultSettemp.next();
            int counttemp = resultSettemp.getInt(1);
            if (counttemp == 0) {
                flag = 0;
            }
            i++;

        }
    }


    //-------Создание таблицы kksoption
    public void SQLCreateKksOption () throws SQLException {
        SQLDeleteFrom("dbselect","kksoption");
        connection.createStatement().executeUpdate("INSERT INTO dbselect.kksoption (cabinet, type, kks, page_no, abonent, page_id, page_esg, page_nobi, module_id, module_addr, imodule_id, value)\n" +
                "    SELECT a.abonent_name, a.block_type, a.page_kks, a.page_no, a.abonent_id, a.page_id, a.page_esg , a.nobi, a.module_id, a.module_addr, a.imodule_id, a.value\n" +
                "      FROM\n" +
                " (SELECT a.abonent_id, a.page_id,\n" +
                "   CASE /*Прописываем значения для загрузки */\n" +
                "   WHEN a.fblock_operator = 'ESG' THEN a.abonent_id || ',' || d.imodule_id || ',' || c.module_id || ',' || right(b.block_type,1) || ',' || a.fblock_channel\n" +
                "   WHEN a.fblock_operator = 'ESG' THEN a.abonent_id || ',' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_operator || ',' || a.fblock_channel\n" +
                "   WHEN a.fblock_operator = 'REG' THEN a.abonent_id || ',' || d.imodule_id || ',' || c.module_id || ',' || right(b.block_type,length(b.block_type)-5) || ',' || a.fblock_channel\n" +
                "   WHEN a.fblock_operator = 'KO' OR a.fblock_operator = 'TE' OR a.fblock_operator = 'VL' OR a.fblock_operator = 'ITE' OR a.fblock_operator = 'IVL' OR a.fblock_operator = 'IBR' THEN a.abonent_id || ',0,0,' || a.fblock_operator || ',' || a.fblock_channel\n" +
                "   WHEN a.fblock_operator = 'KOM' THEN a.abonent_id || ',0,0,' || left(a.fblock_operator,2) || ',' || a.fblock_channel\n" +
                "   WHEN a.fblock_operator = 'ESL' OR a.fblock_operator = 'EML' THEN a.abonent_id || ',' || c.module_id || ',' || a.fblock_operator || ',' || a.fblock_operator_nr\n" +
                "    END\n" +
                "   AS nobi,\n" +
                "   CASE /*Прописываем значения для имитации*/\n" +
                "   WHEN b.block_type LIKE '%MS.AE%' OR b.block_type LIKE '%MS.TE%' THEN a.abonent_id || '_' || 'EAS,' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_channel || '1'\n" +
                "   WHEN b.block_type LIKE '%MS.BE%' OR b.block_type LIKE '%MS.WE%' THEN a.abonent_id || '_' || 'ES,' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_channel || '1'\n" +
                "   WHEN b.block_type LIKE '%MS.AA%' OR b.block_type LIKE '%MS.TA%' THEN a.abonent_id || '_' || 'AAS,' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_channel || '1'\n" +
                "   WHEN b.block_type LIKE '%MS.BA%' OR b.block_type LIKE '%MS.WA%' THEN a.abonent_id || '_' || 'AS,' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_channel || '1'\n" +
                "   WHEN b.block_type LIKE '%ESG%' THEN a.abonent_id || '_' || right(b.block_type,1) || ',' || d.imodule_id || ',' || c.module_id || ',' || a.fblock_channel\n" +
                "         END AS page_esg, b.block_type, d.module_addr, c.module_id, d.imodule_id, e.page_kks, e.page_no, upper(f.cabinet_name) AS abonent_name, g.value\n" +
                " FROM (((((\n" +
                "     dbselect.fblocks AS a\n" +
                "     LEFT JOIN dbselect.blocks AS b\n" +
                "       ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.block_id = b.block_id)\n" +
                "   LEFT JOIN dbselect.functionpages AS c\n" +
                "     ON a.abonent_id = c.abonent_id AND a.page_id = c.page_id)\n" +
                "   LEFT JOIN dbselect.modules AS d\n" +
                "     ON a.abonent_id = d.abonent_id AND c.module_id = d.module_id AND c.imodule_id = d.imodule_id)\n" +
                "   LEFT JOIN dbselect.pages AS e\n" +
                "     ON a.abonent_id = e.abonent_id AND a.page_id = e.page_id)\n" +
                "   LEFT JOIN dbselect.abonents AS f\n" +
                "     ON a.abonent_id = f.abonent_id)\n" +
                "   LEFT JOIN (\n" +
                "               SELECT a.abonent_id, a.page_id, a.block_id, CASE WHEN a.fbport_name = 'XA' THEN a.fbport_value END || '...' || CASE WHEN b.fbport_name = 'XE' THEN b.fbport_value END AS value\n" +
                "               FROM dbselect.fbparameters AS a\n" +
                "                 LEFT JOIN dbselect.fbparameters AS b\n" +
                "                   ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.port_id = b.port_id-1\n" +
                "               WHERE a.fbport_name = 'XA' AND b.fbport_name = 'XE'\n" +
                "               ORDER BY a.abonent_id, a.page_id, a.fbport_name\n" +
                "             ) AS g\n" +
                "   ON a.abonent_id = g.abonent_id AND a.page_id = g.page_id AND a.block_id = g.block_id\n" +
                " WHERE a.fblock_operator IS NOT NULL) AS a");

    }


    //-------Создание таблицы dbconection
    public void SQLCreatedbconnection () throws SQLException {
        connection.createStatement().executeUpdate("INSERT INTO dbselect.dbconnection (pageleft, pageleftno, signalcodein, telegramin, page_kks, page_no, abonent_id, page_id, block_id, port_id, connector_id, connector_port_id, block_name, block_type, signalcode, pageright, pagerightno, telegram, fbport_marker, fbport_techname)\n" +
                " SELECT CASE WHEN b.page <> '' AND b.page_no IS NULL THEN a.page_kks ELSE b.page END AS pageleft, CASE WHEN b.page <> '' AND b.page_no IS NULL THEN b.page ELSE b.page_no END AS pageleftno, b.signalcode AS signalcodein, b.telegram AS telegramin, a.page_kks, a.page_no, a.abonent_id, a.page_id, a.block_id, a.port_id, a.connector_id, a.connector_port_id, a.block_name, a.block_type, a.signalcode, a.pageright, a.pagerightno, a.telegram, a.fbport_marker, a.fbport_techname\n" +
                " FROM(SELECT a.page_kks, a.page_no, a.abonent_id, a.page_id, a.block_id, a.port_id, a.connector_id, a.connector_port_id, a.block_name, a.block_type, b.signalcode, CASE WHEN b.pagerightno IS NULL AND b.pageright <> '' THEN a.page_kks ELSE b.pageright END  AS pageright, CASE WHEN b.pagerightno IS NULL AND b.pageright <> '' THEN b.pageright ELSE b.pagerightno END AS pagerightno, b.telegram, a.fbport_marker, a.fbport_techname\n" +
                "     FROM (SELECT DISTINCT  b.page_kks, b.page_no, a.abonent_id, a.page_id, a.block_id, a.port_id, a.connector_id, a.connector_port_id, a.block_name, a.block_type, a.fbport_marker, a.fbport_techname\n" +
                "           FROM(SELECT a.abonent_id, a.page_id, a.block_id, a.port_id, a.connector_id, a.connector_port_id, a.block_name, a.block_type, b.fbport_marker, b.fbport_techname\n" +
                "                FROM (SELECT a.abonent_id, a.page_id, a.block_id, a.port_id, a.connector_id, a.connector_port_id, b.block_name, b.block_type\n" +
                "                      FROM dbselect.connectors AS a\n" +
                "                        LEFT JOIN dbselect.blocks AS b\n" +
                "                          ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.connector_id = b.block_id) AS a\n" +
                "                  LEFT JOIN dbselect.infbport AS b\n" +
                "                    ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.connector_id = b.block_id AND a.connector_port_id = b.port_id) AS a\n" +
                "             INNER JOIN dbselect.pages AS b\n" +
                "               ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id) AS a\n" +
                "       LEFT JOIN (SELECT a.abonent_id, b.signalcode, a.pageright, a.pagerightno, a.page_id, a.port_id, a.block_id, a.connector_id, a.telegram, a.connector_port_id\n" +
                "                  FROM(SELECT abonent_id, page AS pageright, page_no AS pagerightno, page_id, port_id, block_id, signalcode, telegram, connector_id, connector_port_id\n" +
                "                       FROM dbselect.pageright\n" +
                "                       WHERE page <> '' AND block_id = '0') AS a\n" +
                "                    INNER JOIN(SELECT abonent_id, page_id, port_id, block_id, signalcode, telegram, connector_id, connector_port_id\n" +
                "                               FROM dbselect.pageright\n" +
                "                               WHERE signalcode IS NOT NULL AND block_id = '0') AS b\n" +
                "                      ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.connector_id = b.connector_id AND a.connector_port_id = b.connector_port_id\n" +
                "                  UNION SELECT abonent_id, signalcode, page  as pageright, page_no as pagerightno, page_id, port_id, block_id, connector_id, telegram, connector_port_id\n" +
                "                        FROM dbselect.pageright\n" +
                "                        WHERE page IS NOT NULL AND signalcode IS NOT NULL) as b\n" +
                "         ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.connector_id = b.block_id AND a.connector_port_id = b.port_id) AS a\n" +
                "  LEFT JOIN dbselect.pageleft AS b\n" +
                "    ON a.abonent_id = b.abonent_id AND a.page_id = b.page_id AND a.block_id = b.block_id AND a.port_id = b.port_id");
    }


    //-------Очистка таблиц
    public void SQLClearTable (String schemaname, String tablename) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM " + schemaname + "." + tablename);
    }

    //-------Заполнение таблицы inputsignal
    public void SQLAInputSignal() throws SQLException {
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.pageleft, a.pageleftno, a.signalcodein, a.telegramin, a.abonent_id, a.page_kks, a.page_no\n" +
                " FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                " WHERE ((a.pageleft IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageleft <> b.kks))\n" +
                " ORDER BY a.page_kks, a.page_no");
    }


    //-------Заполнение таблицы outputsignal
    public void SQLOutputSignal() throws SQLException {
        resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.abonent_id, a.page_kks, a.page_no, a.signalcode, a.telegram, a.pageright, a.pagerightno, '' as block, '' as techname, '' as marker\n" +
                " FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                " WHERE ((a.pageright IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageright <> b.kks))\n" +
                " ORDER BY a.page_kks, a.page_no");
    }

    //-------Заполнение таблицы connectiontable
    public void SQLConnectionTable(String value) throws SQLException {

        switch (value) {

            case "[false, false, false, true]" :
                resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                        ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr, '' as het\n" +
                        " FROM (\n" +
                        "  SELECT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, dbselect.findkks b\n" +
                        "  WHERE a.kks = b.kks\n" +
                        ") as a\n" +
                        " ORDER a.kks, a.page_no");
                break;
            case "[true, false, false, true]" :
                resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                        ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr, '' as het\n" +
                        " FROM (\n" +
                        "  SELECT a.cabinet, b.pageleft as kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, (SELECT DISTINCT a.pageleft, a.pageleftno, a.signalcodein, a.telegramin, a.abonent_id, a.page_kks, a.page_no\n" +
                        "       FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                        "       WHERE ((a.pageleft IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageleft <> a.page_kks))\n" +
                        "       ORDER BY a.pageleft) b\n" +
                        "  WHERE  a.kks = b.pageleft\n" +
                        "  UNION ALL\n" +
                        "  SELECT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, dbselect.findkks b\n" +
                        "  WHERE a.kks = b.kks\n" +
                        ") as a" +
                        " ORDER a.kks, a.page_no");
                break;
            case "[false, true, false, true]" :
                resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                        ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr, '' as het\n" +
                        " FROM (\n" +
                        "  SELECT a.cabinet, b.pageright as kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, (SELECT DISTINCT a.abonent_id, a.page_kks, a.page_no, a.signalcode, a.telegram, a.pageright, a.pagerightno\n" +
                        "           FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                        "           WHERE ((a.pageright IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageright <> b.kks))\n" +
                        "           ORDER BY a.pageright) b\n" +
                        "  WHERE a.kks = b.pageright\n" +
                        "  UNION ALL\n" +
                        "  SELECT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, dbselect.findkks b\n" +
                        "  WHERE a.kks = b.kks\n" +
                        ") as a" +
                        " ORDER a.kks, a.page_no");
                break;
            case "[true, true, false, true]" :
                resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                        ,CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr, '' as het\n" +
                        " FROM (\n" +
                        "  SELECT a.cabinet, b.pageleft as kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, (SELECT DISTINCT a.pageleft, a.pageleftno, a.signalcodein, a.telegramin, a.abonent_id, a.page_kks, a.page_no\n" +
                        "           FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                        "           WHERE ((a.pageleft IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageleft <> a.page_kks))\n" +
                        "           ORDER BY a.pageleft) b\n" +
                        "  WHERE  a.kks = b.pageleft\n" +
                        "  UNION ALL\n" +
                        "  SELECT a.cabinet, b.pageright as kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, (SELECT DISTINCT a.abonent_id, a.page_kks, a.page_no, a.signalcode, a.telegram, a.pageright, a.pagerightno\n" +
                        "           FROM dbselect.dbconnection AS a, dbselect.findkks AS b\n" +
                        "           WHERE ((a.pageright IS NOT NULL) AND (a.page_kks = b.kks) AND (a.pageright <> b.kks))\n" +
                        "           ORDER BY a.pageright) b\n" +
                        "  WHERE  a.kks = b.pageright\n" +
                        "  UNION ALL\n" +
                        "  SELECT a.cabinet, a.kks, a.page_no, a.page_esg, a.page_nobi, a.value, a.module_addr\n" +
                        "  FROM dbselect.kksoption a, dbselect.findkks b\n" +
                        "  WHERE a.kks = b.kks\n" +
                        ") as a" +
                        " ORDER a.kks, a.page_no");
                break;
            default:
                System.out.println("Запрос соединений не отработал\n");
                break;
        }
    }



    public static void main(String[] args) {

    }

}
