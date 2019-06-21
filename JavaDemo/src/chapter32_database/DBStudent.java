package chapter32_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBStudent {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/studentdb2";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static boolean connect() {
        boolean s = true;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = state.executeQuery("SELECT * FROM student");
            s = true;
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
            s = false;
        }
        return s;
    }
//參數可以是Student物件引數可以比較精簡

    public static boolean insert2(students st) {
        boolean sucess = false;
        String sql = String.format("Insert into student(student_id,name,phone) "
                + "values ('%s','%s','%s')", st.getId(), st.getName(), st.getPhone());
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM student");
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫操作出問題:" + ex.toString());
        }
        return sucess;
    }

    public static boolean insert(String student_id, String name, String phone) {
        boolean sucess = false;
        //String sql_insert ="Insert Into student (student_id,name,phone) values ('u124','摮怠之瘥�','0965521126')";
        String sql_insert = String.format("Insert Into student (student_id,name,phone) "
                + "values ('%s','%s','%s')",
                student_id, name, phone);
        //System.out.println(sql_insert);
        try {
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
            resultSet = state.executeQuery("select * from student");
        } catch (SQLException ex) {
            System.out.println("selectAll出問題:" + ex.toString());
        }
    }

    public static void selectAll() {

        // select  
        String sql = "select * from student";

        try {
            resultSet = state.executeQuery(sql);
            while (resultSet.next()) {
                System.out.printf("學號:%s  姓名:%s  手機:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //���摰���
    public static boolean selectByName(String name) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from student where name like '%s%s'", name, "%");
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName出問題:" + ex.toString());
        }
        return sucess;
    }

    //�����tudent_id
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
            System.out.println("資料庫selectByID出問題:" + ex.toString());
        }
        return sucess;
    }

    //����蝑�
    public static boolean update(String id, String name, String phone) {
        boolean sucess = false;
        String sql = String.format("update student SET name='%s',phone='%s' where student_id='%s'", name, phone, id);
        try {
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM student");//��憟賣�������
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫update出問題:" + ex.toString());
        }
        return sucess;
    }
    //����蝑�

    public static boolean delete(String id) {
        String sql = String.format("delete from student where student_id='%s'", id);
        boolean sucess = false;
        try {
            sucess = state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM student");
        } catch (SQLException ex) {
            System.out.println("資料庫delete連線出問題:" + ex.toString());
        }
        return sucess;
    }

    //���������
    public static void print() {
        try {
            while (resultSet.next()) {
                System.out.printf("學號:%s   姓名:%s  手機:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //�����esultSet��������蒂��Student�隞嗥�ist
    public static List<Student> getAllStudent() {
        List<Student> results = new ArrayList();
        try {
            while (resultSet.next()) {
                results.add(new Student(
                        resultSet.getString("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone")));
            } // end while
        } // end try
        catch (SQLException ex) {
            System.out.println("資料庫getAllStudent出問題:" + ex.toString());
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

    //Terminal印出目前的資料寫法2
    public static void print2(List<students> students) {
        students.forEach((student) -> {
            System.out.printf("%s %s %s\n", student.getId(), student.getName(), student.getPhone());
        });
    }

    public static void print(List<Student> students) {
        for (Student st : students) {
            System.out.printf("%s %s %s\n", st.getId(), st.getName(), st.getPhone());
        }
    }

    //測試一下資料庫操作是否能成功
    public static void main(String[] args) {
        DBStudent.connect();
        List<students> results;
        print(DBStudent.getAllStudent());
//Studentdb.insert("u127", "馬大牌4", "0012");
//results = DBStudent.getAllStudent();
//print(DBStudent.getAllStudent());
    }
}
