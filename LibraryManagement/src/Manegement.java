// --== CS400 File Header Information ==--
// Name: Ruoran Huang
// Email: rhuang93@wisc.edu
// Notes to Grader: <optional extra notes>

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Manegement {

    private static HashTableMap shelve;
    private LinkedList<Book> bookLinkedList;


    //This method will a return all the book in the hashtable
    public static LinkedList<Book> getBookList() {
        LinkedList<Book> bookList;
        loadData();
        bookList = shelve.iterator();
        return bookList;
    }


    //this method is used to load the information of the book from the json file
    public static void loadData() {
        JSONParser jp = new JSONParser();
        try  {
            Object obj = jp.parse(new FileReader("src/books.json"));
            JSONArray jsonObject = (JSONArray) obj;
            Iterator iterator = jsonObject.iterator();
            shelve = new HashTableMap((int) (jsonObject.size() * 1.5));
            while (iterator.hasNext()) {
                JSONObject bookObj = (JSONObject) iterator.next();
                JSONObject s = (JSONObject) bookObj.get("book");
                String bookName = (String) s.get("BookName");
                String author = (String) s.get("Author");
                String year = (String) s.get("Year");
                String status = (String) s.get("status");

                int Year = -1;
                if (isNumeric(year)) {
                    Year = Integer.parseInt(year);
                } else {
                    Year = -1;
                }
                Book newBook = new Book(bookName, author, Year);
                //if the book is issued
                if (status.equals("Borrowed")) {
                    newBook.issue();
                }
                shelve.put(bookName, newBook);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //helper method to check if the string is number
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    //this method is used to add new book to the library
    public static void addBook(Book book) {
        loadData();
        String bookName = book.getBookName();
        String author = book.getAuthor();
        int year = book.getYear();
        if (bookName != null) {
            if (author == null) {
                author = "unknown";
                Book newBook = new Book(bookName, author, year);
                shelve.put(bookName, newBook);
                storeJson(newBook, true);
            } else {
                shelve.put(bookName, book);
                storeJson(book, true);
            }
        }
    }

    //this helper method is used to store current hashtable to json
    private static <string> boolean storeJson(Book book, boolean ifNew) {
        JSONParser jp = new JSONParser();
        try {
            Object obj = jp.parse(new FileReader("src/books.json"));
            JSONArray bookList= (JSONArray) obj;
            JSONObject bookDetails = new JSONObject();
            JSONObject bookObject = new JSONObject();
            if (ifNew) {
                bookDetails.put("BookName", book.getBookName());
                bookDetails.put("Author", book.getAuthor());
                bookDetails.put("Year", "" + book.getYear());
                bookDetails.put("status", book.getStatus());
                bookObject.put("book", bookDetails);
                bookList.add(bookObject);
            } else {
                int n = bookList.size();
                for (int i = 0; i < n; i++) {
                    JSONObject bookObj = (JSONObject) bookList.get(i);
                    JSONObject s = (JSONObject) bookObj.get("book");
                    String bookName = (String) s.get("BookName");
                    if (bookName.equals(book.getBookName())) {
                        s.put("BookName", book.getBookName());
                        s.put("Author", book.getAuthor());
                        s.put("Year", "" + book.getYear());
                        s.put("status", book.getStatus());
                        bookObject.put("book", s);
                        bookList.add(bookObject);
                    }
                }
            }
            FileWriter file = new FileWriter("src/books.json");
            file.write(bookList.toJSONString());
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    //This method is used to get a selected book
    public static Book getBook(String name) {
        loadData();
        Book newBook = (Book)shelve.get(name);
        return newBook;
    }



    public static void issueBook(Book book) {
        loadData();
        book.issue();
        shelve.put(book.getBookName(), book);
        storeJson(book, false);
    }

    public static void returnBook(Book book) {
        loadData();
        book.returnBook();
        shelve.put(book.getBookName(), book);
        storeJson(book, false);
    }
    public static void main(String[] args) {
        LinkedList<Book> bookList = getBookList();
        System.out.println(bookList.size());

    }


}
