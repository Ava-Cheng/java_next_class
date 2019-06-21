package chapter32_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBClass {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/studentdb2";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static boolean connect() {
        boolean s = true;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = state.executeQuery("SELECT * FROM class");
            s = true;
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
            s = false;
        }
        return s;
    }
//參數可以是Student物件引數可以比較精簡

    public static boolean insert2(Classes cl) {
        boolean sucess = false;
        String sql = String.format("Insert into student(class_id,class_name,credit,teacher_id) "
                + "values ('%s','%s','%s','%s')", cl.getId(), cl.getName(), cl.getCredit(), cl.getTeacher_id());
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM class");
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫操作出問題:" + ex.toString());
        }
        return sucess;
    }

    public static boolean insert(String class_id, String class_name, String credit, String teacher_id) {
        boolean sucess = false;
        //String sql_insert ="Insert Into student (student_id,name,phone) values ('u124','摮怠之瘥�','0965521126')";
        String sql_insert = String.format("Insert Into class (class_id,class_name,credit,teacher_id) "
                + "values ('%s','%s','%s','%s')",
                class_id, class_name, credit, teacher_id);
        try {
            //System.out.println(sql_insert);
            state.execute(sql_insert);
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫insert出問題:\n" + ex.toString());
        }
        return sucess;

    }

    //���������
    public static void selectAll2() {
        try {
            resultSet = state.executeQuery("select * from class");
        } catch (SQLException ex) {
            System.out.println("selectAll出問題:" + ex.toString());
        }
    }

    public static void selectAll() {

        // select  
        String sql = "select * from class";

        try {
            resultSet = state.executeQuery(sql);
            while (resultSet.next()) {
                System.out.printf("課程ID:%s  課程名稱:%s  學分:%s 老師ID:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //���摰���
    public static boolean selectByName(String class_name) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from class where class_name like '%s%s'", class_name, "%");
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName出問題:" + ex.toString());
        }
        return sucess;
    }

    //�����tudent_id
    public static boolean selectByID(String class_id) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from class where class_id = '%s'", class_id);
            resultSet = state.executeQuery(sql);
            if (resultSet.next()) {
                sucess = true;
            }
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID出問題:" + ex.toString());
        }
        return sucess;
    }

    //����蝑�
    public static boolean update(String class_id, String class_name, String credit, String teacher_id) {
        boolean sucess = false;
        String sql = String.format("update class SET class_name='%s',credit='%s',teacher_id='%s' where class_id='%s'", class_name, credit, teacher_id, class_id);
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM class");//��憟賣�������
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫update出問題:" + ex.toString());
        }
        return sucess;
    }
    //����蝑�

    public static boolean delete(String class_id) {
        String sql = String.format("delete from class where class_id='%s'", class_id);
        boolean sucess = false;
        try {
            sucess = state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM class");
        } catch (SQLException ex) {
            System.out.println("資料庫delete連線出問題:" + ex.toString());
        }
        return sucess;
    }

    //�����esultSet��������蒂��Student�隞嗥�ist
    public static List< Classes> getAllClass() {
        List< Classes> results = new ArrayList();
        try {
            while (resultSet.next()) {
                results.add(new Classes(
                        resultSet.getString("class_id"),
                        resultSet.getString("class_name"),
                        resultSet.getString("credit"),
                        resultSet.getString("teacher_id")));
            } // end while
        } // end try
        catch (SQLException ex) {
            System.out.println("資料庫getAllClasses出問題:" + ex.toString());
        } // end catch
        return results;
    }

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

    public static void print(List<Classes> classes) {
        for (Classes cl : classes) {
            System.out.printf("%s %s %s %s\n", cl.getId(), cl.getName(), cl.getCredit(), cl.getTeacher_id());
        }
    }

    //測試一下資料庫操作是否能成功
    public static void main(String[] args) {
        DBClass.connect();
        List<Classes> results;
        print(DBClass.getAllClass());
//Studentdb.insert("u127", "馬大牌4", "0012");
//results = DBStudent.getAllStudent();
//print(DBStudent.getAllStudent());
    }
}
