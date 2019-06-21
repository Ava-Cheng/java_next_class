package chapter32_database;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CrudStudentTableViewController implements Initializable {

    List<Student> students = null;
    @FXML
    private TableView<Student> table_student;
    @FXML
    private TableColumn<Student, String> col_id;
    @FXML
    private TableColumn<Student, String> col_name;
    @FXML
    private TableColumn<Student, String> col_phone;
    @FXML
    private Pagination pagination;
    private final int RowsPerPage = 2;
    @FXML
    private TextField queryID;
    @FXML
    private TextField queryName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable(); //表格初始化
        connect_db(); //連結到資料庫，並將所有資料顯示到表格
    }

    private void initTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
//按下頁次會驅動的事件，寫法格式有點難理解，說明如後:
//ObservableValue<? extends Number> 是介面，
// ? extends Number 表示某種型態繼承Number類別?表示此型態沒被用到所以用?代替
// changed 有三個參數: ObservableValue、舊的頁次、新的頁次
// ObservableValue是頁次物件的一些屬性印出如下的結果:
//IntegerProperty [bean: Pagination[id=pagination, styleClass=pagination], name: currentPageIndex, value: 1]
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                showTablePage(newValue.intValue(), RowsPerPage);
//System.out.println(observable);
            }
        });
// 表格切換到一下筆，對應的驅動方法，此處暫時沒用到，寫法與前面類似
        table_student.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });
//讓表格內容可以修改
        table_student.setEditable(true);
//表格欄位設定成可以編輯必須分別塞入一個TextFieldTableCell類別元件
        col_id.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_phone.setCellFactory(TextFieldTableCell.forTableColumn());
    }
//表格內容載入

    private void loadTable() {
        int totalPage = (int) (Math.ceil(students.size() * 1.0 / RowsPerPage));
        pagination.setPageCount(totalPage);
//pagination.setCurrentPageIndex(0);
        int currentpg = pagination.getCurrentPageIndex();
        showTablePage(currentpg, RowsPerPage);
    }
//顯示某一個頁面的表格內容

    private void showTablePage(int pg, int row_per_pg) {
        table_student.getItems().clear(); //先清除表格內容
        int from = pg * row_per_pg; //計算在此頁面顯示第幾筆到第幾筆
        int to = Math.min(from + row_per_pg, students.size());
//students一筆一筆加到表格中
        for (int i = from; i < to; i++) {
            table_student.getItems().add(students.get(i));
        }
    }
//學生學號欄位若有修改驅動這個方法

    @FXML
    private void onIdEditCommit(CellEditEvent<Student, String> event) {
//拿到表格中所在的該筆紀錄(是一筆學生物件)
        Student stud = table_student.getSelectionModel().getSelectedItem();
        stud.setId(event.getNewValue()); //將該筆學生物件修改成新的值
    }
//學生姓名欄位若有修改驅動這個方法

    @FXML
    private void onNameEditCommit(CellEditEvent<Student, String> t) {
//取得該筆紀錄的方式有以下3種寫法:
//Student stud = t.getTableView().getItems().get(t.getTablePosition().getRow());
//Student stud = (Student) t.getTableView().getItems().get(t.getTablePosition().getRow());
        Student stud = table_student.getSelectionModel().getSelectedItem();
        stud.setName(t.getNewValue());
//另種寫法2:
//((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
    }
//學生電話欄位若有修改驅動這個方法

    @FXML
    private void onPhoneEditCommit(CellEditEvent<Student, String> event) {
        Student stud = table_student.getSelectionModel().getSelectedItem();
        stud.setPhone(event.getNewValue());
    }
//更新一筆紀錄

    @FXML
    private void update(ActionEvent event) {
        Student stud = table_student.getSelectionModel().getSelectedItem();
        String id = stud.getId();
        String name = stud.getName();
        String phone = stud.getPhone();
        DBStudent.update(id, name, phone);
        students = DBStudent.getAllStudent();
        loadTable();
    }
// 刪除一筆紀錄

    @FXML
    private void delete(ActionEvent event) {
        Student stud = table_student.getSelectionModel().getSelectedItem();
        String id = stud.getId();
        boolean sucess = DBStudent.delete(id);
        students = DBStudent.getAllStudent();
        loadTable();
    }
//新增一筆紀錄

    @FXML
    private void insert(ActionEvent event) {
        Student stud = table_student.getSelectionModel().getSelectedItem();
        String id = stud.getId();
        String name = stud.getName();
        String phone = stud.getPhone();
        DBStudent.insert(id, name, phone);
        students = DBStudent.getAllStudent();
        loadTable();
    }
//新增空白的一筆紀錄

    @FXML
    private void blankRecord(ActionEvent event) {
        table_student.getItems().add(new Student("u012", "李大同1", "12345"));
    }
//搜尋特定學生學號

    @FXML
    private void findID(ActionEvent event) {
        boolean sucess = DBStudent.selectByID(queryID.getText());
        students = DBStudent.getAllStudent();
        loadTable();
    }
//搜尋特定姓名

    @FXML
    private void findName(ActionEvent event) {
        DBStudent.selectByName(queryName.getText());
        students = DBStudent.getAllStudent();
        loadTable();
    }
//搜尋全部

    @FXML
    private void findAll(ActionEvent event) {
        DBStudent.selectAll2();
        students = DBStudent.getAllStudent();
        loadTable();
    }
//連接到資料庫按鈕呼叫的方法

    @FXML
    private void connectToDB(ActionEvent event) {
        connect_db();
    }
//連接到資料庫並將所有資料顯示到表格

    private void connect_db() {
        DBStudent.connect();
        students = DBStudent.getAllStudent();
        loadTable();
    }
}
