package chapter32_database;

import java.util.List;

public class StudentDB_20 {

    public static void main(String[] args) {
        
        DBStudent.connect();
        //DBStudent.selectByName("孫");
        //DBStudent.selectByID("u001");
        
        //DBStudent.selectAll();
        //DBStudent.insert("u126","孫大毛","0965521126");
        //DBStudent.update("u126","孫大毛2","0965521126");
        //DBStudent.delete("u126");
        //DBStudent.selectAll();
        //DBStudent.print();
        
        DBStudent.selectAll();
        
        List<Student> all_students  = DBStudent.getAllStudent();
        
        System.out.println(all_students);
        
 
        for (int i=0; i < all_students.size(); i++)
        {
            System.out.printf("學號:%s   姓名:%s  電話:%s\n", 
                    all_students.get(i).getId(),
                    all_students.get(i).getName(),
                    all_students.get(i).getPhone());
        }
        
    }

}
