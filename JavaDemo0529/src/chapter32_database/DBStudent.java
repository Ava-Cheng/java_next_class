package chapter32_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBStudent {

    private static String DB_URL = "jdbc:mariadb://localhost:3306/studentdb";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static void connect() {

        try {
            conn = DriverManager.getConnection(DB_URL, "mis", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //resultSet = state.executeQuery("SELECT * FROM student");
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
        }
    }

    public static List<Student> getAllStudent() {
        List<Student> results = new ArrayList();

        try {
            while (resultSet.next()) {
                
                results.add(  new Student(
                      resultSet.getString("student_id"),
                      resultSet.getString("name"),
                        resultSet.getString("phone")
                ));
            }

        } catch (SQLException ex) {
            System.out.println("selectAll操作異常:" + ex.toString());
        }

        return results;
    }

    public static void insert(String student_id, String name, String phone) {

        //String sql_insert ="Insert Into student (student_id,name,phone) values ('u124','孫大毛','0965521126')";
        String sql_insert = String.format("Insert Into student (student_id,name,phone) "
                + "values ('%s','%s','%s')",
                student_id, name, phone);
        try {
            state.execute(sql_insert);
        } catch (SQLException ex) {
            System.out.println("資料庫insert出問題:\n" + ex.toString());
        }

    }

    //選擇全部的資料
    public static void selectAll() {
        try {
            resultSet = state.executeQuery("select * from student");
        } catch (SQLException ex) {
            System.out.println("selectAll操作異常:" + ex.toString());
        }
    }

    public static void print() {
        try {

            while (resultSet.next()) {
                System.out.printf("學號:%s   姓名:%s  電話:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }

        } catch (SQLException ex) {
            System.out.println("selectAll操作異常:" + ex.toString());
        }

    }

    public static void selectAll2() {

        // select  
        String sql = "select * from student";

        try {
            resultSet = state.executeQuery(sql);
            while (resultSet.next()) {
                System.out.printf("學號:%s   姓名:%s  電話:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //更新這一筆
    public static boolean update(String id, String name, String phone) {
        boolean sucess = false;
        String sql = String.format("update student SET name='%s',phone='%s' where student_id='%s'", name, phone, id);
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM student");//最好是撈新的資料
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫update操作異常:" + ex.toString());
        }
        return sucess;
    }

    //刪除這一筆
    public static boolean delete(String id) {
        String sql = String.format("delete from student where student_id='%s'", id);
        boolean sucess = false;
        try {
            sucess = state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM student");
        } catch (SQLException ex) {
            System.out.println("資料庫delete操作異常:" + ex.toString());
        }
        return sucess;
    }

    //選擇特定姓名
    public static boolean selectByName(String name) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from student where name like '%s%s'", name, "%");
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName操作異常:" + ex.toString());
        }
        return sucess;
    }
//選擇某個student_id

    public static boolean selectByID(String id) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from student where student_id = '%s'", id);
            resultSet = state.executeQuery(sql);
            if (resultSet.next()) {
                sucess = true;
            }
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID操作異常:" + ex.toString());
        }
        return sucess;
    }

}
