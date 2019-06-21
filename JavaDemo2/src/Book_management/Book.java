package Book_management;

public class Book {

    private String name;
    public String ISBN;
    public String author;
    public String borrower;

    public Book( String ISBN, String name,String author, String borrower) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.borrower = borrower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getName() {
        return name;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrower() {
        return borrower;
    }
}
