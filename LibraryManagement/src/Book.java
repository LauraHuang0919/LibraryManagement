// --== CS400 File Header Information ==--
// Name: Ruoran Huang
// Email: rhuang93@wisc.edu
// Notes to Grader: <optional extra notes>

//This class is used to store the information of book such as the name, author, publish year,
//category.
public class Book {

    private String bookName;
    private String author;
    private int year;

    private String Status;

    private boolean ifChecked;

    public Book(String bookName, String author, int year) {
        this.bookName = bookName;
        this.author = author;
        this.year = year;
        this.ifChecked = false;
        this.Status = "Unborrowed";
    }

    public Book() {
        this.bookName = null;
        this.author = null;
        this.year = -1;
        this.ifChecked = false;
        this.Status = "Unborrowed";
    }
    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getStatus() {
        return Status;
    }

    public void returnBook() {ifChecked=false; Status = "Unborrowed";}
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStatus(String status) {
        if (status.equals("Borrowed")) {
            this.Status = "Borrowed";
            this.ifChecked = true;
        } else {
            this.Status = "Unborrowed";
            this.ifChecked = false;
        }
    }

    public void issue() {
        this.ifChecked = true;
        this.Status = "Borrowed";
    }

}
