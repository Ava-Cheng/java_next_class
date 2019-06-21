package Book_management;

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
import java.util.Scanner;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CrudBookAllController implements Initializable {
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

    @FXML
    private void nextRecord(ActionEvent event) {
        if (current < books.size() - 1) {
            current += 1;
        } else {
            current = 0;
        }
        showRecord();
    }

    @FXML
    private void previousRecord(ActionEvent event) {
        if (current > 0) {
            current -= 1;
        } else {
            current = books.size() - 1;
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

    @FXML
    private void delete(ActionEvent event) {
        String ISBN = books.get(current).getISBN();
        System.out.println(ISBN);
        boolean sucess = DBBook.delete(ISBN);
        showlog("刪除一筆資料", sucess);
        books = DBBook.getAllBook();
        current = 0;
        showRecord();
    }

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

    @FXML
    private void blankRecord(ActionEvent event) {
        bkISBN.setText("");
        bkName.setText("");
        bkAuthor.setText("");
        recordNo.setText("");
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
        showlog("查找作者", sucess);
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

}
