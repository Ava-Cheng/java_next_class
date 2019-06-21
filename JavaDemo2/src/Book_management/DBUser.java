package Book_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUser {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/bookdb";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static boolean connect() {
        boolean s = true;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = state.executeQuery("SELECT * FROM login");
            s = true;
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
            s = false;
        }
        return s;
    }

    public static boolean insert(String userID, String password, String permission) {
        boolean sucess = false;
        String sql_insert = String.format("Insert Into login (userID, password,permission) "
                + "values ('%s','%s','%s')",
                userID, password, permission);
        try {
            state.execute(sql_insert);
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫insert出問題:\n" + ex.toString());
        }
        return sucess;

    }

    //搜尋全部
    public static void selectAll() {
        try {
            resultSet = state.executeQuery("select * from login");
        } catch (SQLException ex) {
            System.out.println("selectAll出問題:" + ex.toString());
        }
    }

    //搜尋使用者ID
    public static boolean selectByID(String userID) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from login where userID like '%s%s'", userID, "%");
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName出問題:" + ex.toString());
        }
        return sucess;
    }

    //更新
    public static boolean update(String userID, String password, String permission) {
        boolean sucess = false;
        String sql;
        sql = String.format("update login SET userID='%s',password='%s',permission='%s' where userID='%s'", userID, password, permission, userID);
        System.out.println("dbuserupdate"+sql);
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM login"); //撈出全部資料
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫update出問題:" + ex.toString());
        }
        
        return sucess;
    }

    //刪除
    public static boolean delete(String userID) {
        String sql = String.format("delete from login where userID='%s'", userID);
        boolean sucess = false;
        try {
            sucess = state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM login");
        } catch (SQLException ex) {
            System.out.println("資料庫delete連線出問題:" + ex.toString());
        }
        return sucess;
    }

    //全部顯示
    public static void print() {
        try {
            while (resultSet.next()) {
                System.out.printf("userID:%s   密碼:%s  權限:%s \n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //取得全部資料
    public static List<User> getAllUser() {
        List<User> results = new ArrayList();
        try {
            while (resultSet.next()) {
                results.add(new User(
                        resultSet.getString("userID"),
                        resultSet.getString("password"),
                        resultSet.getString("permission")));
            } // end while
        } // end try
        catch (SQLException ex) {
            System.out.println("資料庫getAllUser出問題:" + ex.toString());
        } // end catch
        return results;
    }

    //關閉連線
    public static boolean closeConnection() {
        boolean sucess = false;
        try {
            conn.close();
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫操作出問題:" + ex.toString());
        }
        return sucess;
    }

    //印出來
    public static void print(List<User> Users) {
        for (User ur : Users) {
            System.out.printf("%s %s %s\n", ur.getId(), ur.getPassword(), ur.getPermission());
        }
    }

    //測試一下資料庫操作是否能成功
    public static void main(String[] args) {
        DBUser.connect();
        List<User> results;
        print(DBUser.getAllUser());
    }
}
