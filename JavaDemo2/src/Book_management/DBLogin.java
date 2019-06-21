package Book_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBLogin {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/bookdb";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static boolean connect() {
        boolean s = true;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            s = true;
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
            s = false;
        }
        return s;
    }

    public static boolean login(String user, String password) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from login where userID='%s' and password='%s'", user, password);
            //System.out.println(sql);
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫login出問題:" + ex.toString());
        }
        return sucess;
    }
}
