package Book_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBBook {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/bookdb";
    private static Connection conn = null;
    private static Statement state = null;
    private static ResultSet resultSet = null;

    public static boolean connect() {
        boolean s = true;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "mis123");
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = state.executeQuery("SELECT * FROM books");
            s = true;
        } catch (SQLException ex) {
            System.out.println("資料庫連線出問題:" + ex.toString());
            s = false;
        }
        return s;
    }

    public static boolean insert(String book_ISBN, String book_name, String book_author, String book_borrower) {
        boolean sucess = false;
        String sql_insert = String.format("Insert Into books (book_ISBN,book_name,book_author,book_borrower) "
                + "values ('%s','%s','%s','%s')",
                book_ISBN, book_name, book_author, book_borrower);
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
            resultSet = state.executeQuery("select * from books");
        } catch (SQLException ex) {
            System.out.println("selectAll出問題:" + ex.toString());
        }
    }

    //搜尋書名
    public static boolean selectByName(String book_name) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from books where book_name like '%s%s'", book_name, "%");
            resultSet = state.executeQuery(sql);
            sucess = resultSet.next();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName出問題:" + ex.toString());
        }
        return sucess;
    }

    //搜尋ISBN
    public static boolean selectByISBN(String book_ISBN) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from books where book_ISBN = '%s'", book_ISBN);
            resultSet = state.executeQuery(sql);
            if (resultSet.next()) {
                sucess = true;
            }
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByISBN出問題:" + ex.toString());
        }
        return sucess;
    }

    //搜尋作者
    public static boolean selectByAuthor(String book_author) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from books where book_author = '%s'", book_author);
            resultSet = state.executeQuery(sql);
            if (resultSet.next()) {
                sucess = true;
            }
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByAuthor出問題:" + ex.toString());
        }
        return sucess;
    }

    //搜尋借閱人
    public static boolean selectByBorrower(String book_borrower) {
        boolean sucess = false;
        try {
            String sql = String.format("select * from books where book_borrower = '%s'", book_borrower);
            resultSet = state.executeQuery(sql);
            if (resultSet.next()) {
                sucess = true;
            }
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("資料庫selectByBorrower出問題:" + ex.toString());
        }
        return sucess;
    }
    
    //更新
    public static boolean update(String book_ISBN, String book_name, String book_author, String book_borrower) {
        boolean sucess = false;
        String sql;
        if (book_borrower == null) {
            //不更新book_borrower
            sql = String.format("update books SET book_ISBN='%s',book_name='%s',book_author='%s' where book_ISBN='%s'", book_ISBN, book_name, book_author, book_ISBN);
        } else {
            //更新book_borrower (空字串會更新)
            sql = String.format("update books SET book_ISBN='%s',book_name='%s',book_author='%s',book_borrower='%s' where book_ISBN='%s'", book_ISBN, book_name, book_author, book_borrower,book_ISBN);
        }
        try {
            System.out.println(sql);
            state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM books"); //撈出全部資料
            sucess = true;
        } catch (SQLException ex) {
            System.out.println("資料庫update出問題:" + ex.toString());
        }
        return sucess;
    }

    //刪除
    public static boolean delete(String book_ISBN) {
        String sql = String.format("delete from books where book_ISBN='%s'", book_ISBN);
        boolean sucess = false;
        try {
            sucess = state.execute(sql);
            resultSet = state.executeQuery("SELECT * FROM books");
        } catch (SQLException ex) {
            System.out.println("資料庫delete連線出問題:" + ex.toString());
        }
        return sucess;
    }

    //全部顯示
    public static void print() {
        try {
            while (resultSet.next()) {
                System.out.printf("ISBN:%s   書名:%s  作者:%s  借閱人:%s\n",
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫select出問題:\n" + ex.toString());
        }
    }

    //取得全部資料
    public static List<Book> getAllBook() {
        List<Book> results = new ArrayList();
        try {
            while (resultSet.next()) {
                results.add(new Book(
                        resultSet.getString("book_ISBN"),
                        resultSet.getString("book_name"),
                        resultSet.getString("book_author"),
                        resultSet.getString("book_borrower")));
            } // end while
        } // end try
        catch (SQLException ex) {
            System.out.println("資料庫getAllBook出問題:" + ex.toString());
        } // end catch
        return results;
    }

    //取得全部資料
    public static String getBookBorrower() {
        List<Book> results = new ArrayList();
        String book_borrower="";
        try {
            while (resultSet.next()) {
                results.add(new Book(
                        resultSet.getString("book_ISBN"),
                        resultSet.getString("book_name"),
                        resultSet.getString("book_author"),
                        resultSet.getString("book_borrower")));
                book_borrower=resultSet.getString("book_borrower");
            } // end while
        } // end try
        catch (SQLException ex) {
            System.out.println("資料庫getAllBook出問題:" + ex.toString());
        } // end catch
        return book_borrower;
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
    public static void print(List<Book> Books) {
        for (Book bk : Books) {
            System.out.printf("%s %s %s %s\n", bk.getName(), bk.getISBN(), bk.getAuthor(), bk.getBorrower());
        }
    }

    //測試一下資料庫操作是否能成功
    public static void main(String[] args) {
        DBBook.connect();
        List<Book> results;
        print(DBBook.getAllBook());
    }
}
