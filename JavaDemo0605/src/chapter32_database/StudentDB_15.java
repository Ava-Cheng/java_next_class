package chapter32_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDB_15 {

    public static void main(String[] args) {
        
        
        String DB_URL = "jdbc:mariadb://localhost:3306/studentdb";
        Connection conn = null;
        Statement state = null;
        ResultSet result = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "mis", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
        }

        // update        
        String sql_update ="update student set name = '孫小毛' where student_id = 'u003' ";        
        try{            
          state.execute( sql_update );      
        }catch (SQLException ex) {
            System.out.println("資料庫update出問題:\n" + ex.toString());
        }        
           
        /*
        // insert
        String sql_insert ="Insert Into student (student_id,name,phone) values ('u124','孫大毛','0965521126')";       
        try{            
          state.execute( sql_insert );      
        }catch (SQLException ex) {
            System.out.println("資料庫insert出問題:\n" + ex.toString());
        }    
       */
        
       // delete
        String sql_delete ="delete from student where student_id = 'u123';";       
        try{            
          state.execute( sql_delete );      
        }catch (SQLException ex) {
            System.out.println("資料庫delete出問題:\n" + ex.toString());
        }    
                
        //**  select  
        String sql = "select * from student";

        try {
            result = state.executeQuery(sql);
            while (result.next()) {
                System.out.printf("學號:%s   姓名:%s  電話:%s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }  
    }

}
