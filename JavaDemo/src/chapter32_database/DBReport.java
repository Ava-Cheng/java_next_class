package chapter32_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBReport {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/studentdb2";
    private static final String USER = "root";
    private static final String PASS = "mis123";
    private static Connection conn = null;
    private static Statement state = null;

    //必須連線一次使用全域變數conn, state
    public static boolean connect() {
        boolean sucess = false;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫操作異常:" + ex.toString());
        }
        return sucess;
    }

    //學生修課報表-----
    public static String reportStudClass(String student_id) {
        String msg;
        String report = "";
        ResultSet rs;
        String sql = String.format("SELECT student_class.student_id, student.name, class_name, class.credit, score "
                + "FROM student_class Inner join student on student_class.student_id = student.student_id "
                + "INNER JOIN class ON student_class.class_id = class.class_id "
                + "WHERE student_class.student_id='%s'", student_id);
        try {
//Connection conn = DriverManager.getConnection(DB_URL, USER , PASS); //使用全域變數，不必再連一次
//state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = state.executeQuery(sql);
            if (rs.next() == false) {
                return "查無資料!";
            } else {
                msg = String.format("學號:%s\t姓名:%s\n", rs.getString("student_id"), rs.getString("name"));
                report += msg;
                report += "課名\t學分\t分數\n";
                report += "-------------------------------------------------------------\n";

                do {
                    msg = String.format("%s\t%s\t%s\n", rs.getString("class_name"), rs.getString("credit"), rs.getString("score"));
                    report += msg;
                } while (rs.next());
                report += "-------------------------------------------------------------\n";
                String sql2 = String.format("SELECT COUNT(*) as 修課數, SUM(credit) as 總學分, AVG(score) as 平均 "
                        + "FROM student_class "
                        + "INNER JOIN class on student_class.class_id = class.class_id "
                        + "WHERE student_id='%s'", student_id);
                rs = state.executeQuery(sql2);
                rs.first();
                msg = String.format("修課數:%s\t總學分:%s\t平均成績:%s\n", rs.getString("修課數"), rs.getString("總學分"), rs.getString("平均"));
                report += msg;
            }
        } catch (SQLException ex) {
            System.out.println("資料庫操作異常:" + ex.toString());
        }
        return report;
    }

    public static void main(String[] args) {
        DBReport.connect();
        String report = DBReport.reportStudClass("u001");
        System.out.println(report);

    }

    static String reportTeacherStud(String teacher_id) {
        String msg;
        String report = "";
        ResultSet rs;
        String sql = String.format("SELECT student_advisor.student_id, student.name "
                + "FROM student_advisor INNER JOIN "
                + "student ON student_advisor.student_id = student.student_id "
                + "WHERE student_advisor.teacher_id = '%s'", teacher_id);
        String sql_teacher = String.format("SELECT name, room, dept "
                + "FROM teacher "
                + "WHERE teacher_id = '%s'", teacher_id);
        try {
            rs = state.executeQuery(sql_teacher);
            if (rs.next() == false) {
                return "查無資料!";
            } else {
                rs.first();
                msg = String.format("教師:%s\t研究室:%s\t部門:%s\n", rs.getString("name"), rs.getString("room"), rs.getString("dept"));
                report += msg;
                rs = state.executeQuery(sql);
                rs.first();
                report += "學號\t姓名\n";
                report += "-------------------------------------------------------------\n";
                do {
                    msg = String.format("%s\t%s\n", rs.getString("student_id"), rs.getString("name"));
                    report += msg;
                } while (rs.next());
                report += "-------------------------------------------------------------\n";
            }
        } catch (SQLException ex) {
            System.out.println("資料庫操作異常:" + ex.toString());
        }
        return report;
    }

    static String reportStudAdvisor() {
        String msg;
        String report = "";
        ResultSet rs;
        String sql = String.format("SELECT student.student_id AS '學號', student.name AS '姓名', teacher.Name AS '老師'\n"
                + "FROM student_advisor\n"
                + "INNER JOIN student ON student_advisor.Student_id = student.student_id \n"
                + "INNER JOIN teacher ON student_advisor.Teacher_id = teacher.Teacher_id;");
        try {
            rs = state.executeQuery(sql);
            rs.first();
            report += "學號\t姓名\t導師\n";
            report += "-------------------------------------------------------------\n";
            do {
                msg = String.format("%s\t%s\t%s\n", rs.getString("student.student_id"), rs.getString("姓名"), rs.getString("老師"));
                report += msg;
            } while (rs.next());
            report += "-------------------------------------------------------------\n";
        } catch (SQLException ex) {
            System.out.println("資料庫操作異常:" + ex.toString());
        }
        return report;
    }

}
