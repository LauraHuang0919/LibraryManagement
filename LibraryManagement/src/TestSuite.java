// --== CS400 File Header Information ==--
// Name: Ruoran Huang
// Email: rhuang93@wisc.edu
// Notes to Grader: <optional extra notes>

import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class TestSuite {

    //this method is used to test if the data can be imported from json file
    @Test
    public void testImportJson() throws IOException, ParseException {
        JSONParser jp = new JSONParser();
        Object obj = jp.parse(new FileReader("src/books.json"));
        JSONArray jsonObject = (JSONArray) obj;
        assertTrue(jsonObject != null);
    }

    //this method is used to test if the book object can be gained from hashtable
    @Test
    public void testGetBook() {
        Manegement.loadData();
        Book book = Manegement.getBook("Opposing Aggression");
        assertTrue(book.getBookName().equals("Opposing Aggression"));
        assertTrue(book.getAuthor().equals("Lu xun"));
        assertTrue(book.getYear() == 1934);
    }

    //this method is used to test if the new book can be added to json file
    @Test
    public void testAddBook() {
        Book newBook = new Book("Opposing Aggression", "Lu xun", 1934);
        Manegement.addBook(newBook);
        Manegement.loadData();
        Book book = Manegement.getBook("Opposing Aggression");
        assertTrue(book.getBookName().equals("Opposing Aggression"));
        assertTrue(book.getAuthor().equals("Lu xun"));
        assertTrue(book.getYear() == 1934);
        assertTrue(book.getStatus().equals("Unborrowed"));
    }

    //this method is used to test if the book can be issued
    @Test
    public void testIssueBook() {
        Manegement.loadData();
        Book book = Manegement.getBook("Opposing Aggression");
        book.issue();
        assertTrue(book.getStatus().equals("Borrowed"));
    }

    //this method is used to test if the book can be returned
    @Test
    public void testReturnBook() {
        Manegement.loadData();
        Book book = Manegement.getBook("Opposing Aggression");
        book.returnBook();
        assertTrue(book.getStatus().equals("Unborrowed"));
    }


}
