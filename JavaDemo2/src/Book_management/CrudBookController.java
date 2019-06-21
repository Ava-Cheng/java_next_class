package Book_management;

import static Book_management.bookManagementController.loginID;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.FileNotFoundException;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CrudBookController implements Initializable {

    //底層
    @FXML
    private AnchorPane baseAnchorPane;

    //查詢
    @FXML
    private TextField bkISBN;
    @FXML
    private TextField bkName;
    @FXML
    private TextField bkAuthor;
    @FXML
    private TextField recordNo;

    //特定查詢
    @FXML
    private TextField queryName;
    @FXML
    private TextField queryISBN;
    @FXML
    private TextField queryAuthor;

    @FXML
    private TextArea file_display;
    @FXML
    private TextArea status;

    Calendar time = Calendar.getInstance();
    List<Book> books = null;
    int current = 0;
    int book_size = 0;

    private TextArea input;
    private String[] names;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (DBBook.connect()) {
            books = DBBook.getAllBook();
            showRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    private void showRecord() {
        if (!books.isEmpty()) {
            bkISBN.setText(books.get(current).getISBN());
            bkName.setText(books.get(current).getName());
            bkAuthor.setText(books.get(current).getAuthor());
            String msg = String.format("第%d 筆/共%d 筆", current + 1, books.size());
            recordNo.setText(msg);
        } else {
//return;
        }
    }

    //前一筆
    @FXML
    private void previousRecord(ActionEvent event) {
        if (current > 0) {
            current -= 1;
        } else {
            current = books.size() - 1;
        }
        showRecord();
    }

    //下一筆
    @FXML
    private void nextRecord(ActionEvent event) {
        if (current < books.size() - 1) {
            current += 1;
        } else {
            current = 0;
        }
        showRecord();
    }

    //第一筆
    @FXML
    private void firstRecord(ActionEvent event) {
        current = 0;
        showRecord();
    }

    //最後一筆
    @FXML
    private void lastRecord(ActionEvent event) {
        current = books.size() - 1;
        showRecord();
    }

    //更新
    @FXML
    private void update(ActionEvent event) {
        String ISBN = bkISBN.getText();
        String name = bkName.getText();
        String author = bkAuthor.getText();
        String borrower = null;
        //borrower如果為空將不更新這欄位
        boolean sucess = DBBook.update(ISBN, name, author, borrower);
        if (sucess) {
            status.appendText(time.getTime() + "更新成功\n");
            books = DBBook.getAllBook();
        } else {
            status.appendText(time.getTime() + "更新失敗\n");
        }
    }

    //刪除
    @FXML
    private void delete(ActionEvent event) {
        String ISBN = books.get(current).getISBN();
        boolean sucess = DBBook.delete(ISBN);
        showlog("刪除一筆資料", sucess);
        books = DBBook.getAllBook();
        current = 0;
        showRecord();
    }

    //插入
    @FXML
    private void insert(ActionEvent event) {
        String ISBN = bkISBN.getText();
        String name = bkName.getText();
        String author = bkAuthor.getText();
        String borrower = "";
        //borrower如果為空將不更新這欄位
        boolean sucess = DBBook.insert(ISBN, name, author, borrower);
        showlog("新增一筆資料", sucess);
        books = DBBook.getAllBook();
        showRecord();
    }

    //找ISBN
    @FXML
    private void findISBN(ActionEvent event) {
        boolean sucess = DBBook.selectByISBN(queryISBN.getText());
        if (sucess) {
            books = DBBook.getAllBook();
            current = 0;
            showRecord();
            status.appendText(time.getTime() + "查找ISBN成功\n");
        } else {
            status.appendText(time.getTime() + "查找ISBN失敗\n");
        }
    }

    //找書名
    @FXML
    private void findName(ActionEvent event) {
        boolean sucess = DBBook.selectByName(queryName.getText());
        showlog("查找書名", sucess);
        books = DBBook.getAllBook();
        current = 0;
        showRecord();
    }

    //找作者
    @FXML
    private void findAuthor(ActionEvent event) {
        boolean sucess = DBBook.selectByAuthor(queryAuthor.getText());
        showlog("查找姓名", sucess);
        books = DBBook.getAllBook();
        current = 0;
        showRecord();
    }

    //尋找全部
    @FXML
    private void findAll(ActionEvent event) {
        DBBook.selectAll();
        books = DBBook.getAllBook();
        showRecord();
    }

    //資料庫連線
    @FXML
    private void connectToDB(ActionEvent event) {
        if (DBBook.connect()) {
            books = DBBook.getAllBook();
            showRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    //關閉連線
    @FXML
    private void closeConnection(ActionEvent event) {
        boolean sucess = DBBook.closeConnection();
        showlog("關閉連線", sucess);
        books = null;
        bkISBN.setText("");
        bkName.setText("");
        bkAuthor.setText("");
        recordNo.setText("");
    }

    //顯示log
    public void showlog(String msg, boolean sucess) {
        if (sucess) {
            status.appendText(time.getTime() + msg + " 成功\n");
        } else {
            status.appendText(time.getTime() + msg + "失敗\n");
        }
    }

    //借書
    @FXML
    private void borrowingBooks(ActionEvent event) {
        String ISBN = bkISBN.getText();
        String name = bkName.getText();
        String author = bkAuthor.getText();
        String borrower = loginID;

        //找尋ISBN
        boolean sucess = DBBook.selectByISBN(ISBN);
        String borrowerName = "";
        boolean update_sucess = false;
        if (sucess) {
            borrowerName = DBBook.getBookBorrower();
            //確認這本書沒人借
            if (borrowerName.length() == 0) {
                try {
                    /*謝謝
                    System.out.println(ISBN);
                    System.out.println(name);
                    System.out.println(author);
                    System.out.println(borrower);
                     */
                    update_sucess = DBBook.update(ISBN, name, author, borrower);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                if (update_sucess) {
                    status.appendText(time.getTime() + "借閱新增成功\n");
                    books = DBBook.getAllBook();
                    showRecord();
                } else {
                    status.appendText(time.getTime() + "借閱新增失敗\n");
                }
            } else {
                //彈出已被借走訊息

                JOptionPane pane = new JOptionPane();
                JDialog dialog = pane.createDialog("訊息");
                JOptionPane.showMessageDialog(null, "已被借走，借閱失敗");
            }
            status.appendText(time.getTime() + "查找ISBN成功\n");
        } else {
            status.appendText(time.getTime() + "查找ISBN失敗\n");
        }
    }

    //借書明細
    @FXML
    private void bookDetails(ActionEvent event) {
        boolean sucess = DBBook.selectByBorrower(loginID);
        showlog("查找借書明細", sucess);
        books = DBBook.getAllBook();
        current = 0;
        showRecord();
    }

    //還書
    @FXML
    private void returningBooks(ActionEvent event) {
        String ISBN = bkISBN.getText();
        String name = bkName.getText();
        String author = bkAuthor.getText();
        String borrower = loginID;

        //找尋ISBN
        boolean sucess = DBBook.selectByISBN(ISBN);
        String borrowerName = "";
        boolean update_sucess = false;
        if (sucess) {
            borrowerName = DBBook.getBookBorrower();
            System.out.println("loginID: " + loginID);
            System.out.println("borrowerName: " + borrowerName);
            if (borrowerName.compareTo(borrower) == 0) {
                try {
                    //還書
                    update_sucess = DBBook.update(ISBN, name, author, "");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                if (update_sucess) {
                    status.appendText(time.getTime() + "還書成功\n");
                    //更新借書紀錄
                    DBBook.selectByBorrower(loginID);
                    books = DBBook.getAllBook();
                    showRecord();
                } else {
                    status.appendText(time.getTime() + "還書失敗\n");
                }
            } else {
                //彈出已被借走訊息
                JOptionPane pane = new JOptionPane();
                JDialog dialog = pane.createDialog("訊息");
                JOptionPane.showMessageDialog(null, "你沒借這本啦");
            }
            status.appendText(time.getTime() + "查找ISBN成功\n");
        } else {
            status.appendText(time.getTime() + "查找ISBN失敗\n");
        }
    }
}
