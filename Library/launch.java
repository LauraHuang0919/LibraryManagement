// --== CS400 File Header Information ==--
// Name: Ruoran Huang
// Email: rhuang93@wisc.edu
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class launch {

    private static Scanner input;



    public static void main(String args[]) throws Exception{

        input = new Scanner(System.in);
        launch();

    }

    private static void launch() {
        menu();
        String command = getCommand();
        execute(command);
    }

    //This method will show the menu of application
    private static void menu() {
        System.out.println("============================================");
        System.out.println("          Library Manage System             ");
        System.out.println("============================================");
        System.out.println("|Add Book...............................[1]|");
        System.out.println("|Issue Book ............................[2]|");
        System.out.println("|Return Book............................[3]|");
        System.out.println("|Check Status...........................[4]|");
        System.out.println("|Help...................................[5]|");
        System.out.println("|Quit...................................[6]|");
        System.out.println("============================================");
    }

    //This method will ask user to type in command
    private static String getCommand() {
        System.out.println("Enter the command of the function: ");
        String command = input.nextLine();
        return command;
    }

    //This method will operate according to the command that user types in
    private static void execute(String command) {
        if (command.equals("1")) {
            addBook();
        } else if (command.equals("2")) {
            IssueBook();
        } else if (command.equals("3")) {
            returnBook();
        } else if (command.equals("4")) {
            checkStatus();
        } else if (command.equals("5") || command.equalsIgnoreCase("Help")) {
            help();
        } else if (command.equals("6") || command.equalsIgnoreCase("Quit")) {
            quit();
        } else {
            String Command = (String) getCommand();
            execute(Command);
        }
    }

    //This method will function as add new book to database
    private static void addBook() {
        System.out.println("============================================");
        System.out.println("Enter the name of the new book: ");
        String Input = input.nextLine();
        Input = name(Input);
        while (Input == null) {
            if (Input.equalsIgnoreCase("Quit") || Input.equalsIgnoreCase("q") || Input.equalsIgnoreCase("[quit]")) {
                launch();
            }
            System.out.println("Please Enter the name of the new book again or Enter [Quit] to quit: ");
            Input = input.nextLine();
            Input = name(Input);
        }
        System.out.println("Enter the author of the book: ");
        String authorName = input.nextLine();
        authorName = name(authorName);
        while (authorName == null) {
            if ((authorName.equalsIgnoreCase("Quit") || authorName.equalsIgnoreCase("q") || authorName.equalsIgnoreCase("[quit]"))) {
                launch();
            }
            System.out.println("Please Enter the author name again or Enter [Quit] to quit: ");
            authorName = input.nextLine();
            authorName = name(authorName);
        }
        System.out.println("Enter the publish year of the book: ");
        String year = input.nextLine();
        while (!Manegement.isNumeric(year)) {
            if ((year.equalsIgnoreCase("Quit") || year.equalsIgnoreCase("q") || year.equalsIgnoreCase("[quit]"))) {
                launch();
            }
            System.out.println("Please Enter a number for published year or Enter [Quit] to quit: ");
            year = input.nextLine();
        }
        Book newBook = new Book(Input, authorName, Integer.parseInt(year));
        Manegement.addBook(newBook);
        System.out.println("============================================");
        System.out.println("Book Added successfully! ");
        System.out.println("Here is the information of new book: ");
        System.out.println("Book Name: " + Input + "; Author Name: " + authorName + "; Publish Year: " + year);
        System.out.println("============================================");
        continueSearch();
    }

    //Deal with the input that user type in
    private static String name(String input) {
        if (input.length() == 0) {
            return "";
        }
        String result ="";
        String[] list = input.split(" ");
        result = result + list[0].substring(0, 1).toUpperCase() + list[0].substring(1).toLowerCase();
        for (int i = 1; i < list.length; i++) {
            result = result + " " + list[i].substring(0, 1).toUpperCase() + list[i].substring(1).toLowerCase();
        }
        return result;
    }

    //This method will ask user if he/she wants to continue to add new book
    private static void continueSearch() {
        System.out.println("Do you want to continue? Yes/No");
        String Input = input.nextLine();
        if (Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y")) {
            addBook();
        } else if (Input.equalsIgnoreCase("No") || Input.equalsIgnoreCase("N")) {
            launch();
        } else {
            continueSearch();
        }
    }

    //This method will issue a book if the book is ready to borrowed
    private static void IssueBook() {
        System.out.println("============================================");
        System.out.println("Here are the books you can borrow: ");
        System.out.println();
        LinkedList<Book> bookList = Manegement.getBookList();
        for (Book book:bookList) {
            if (book.getStatus().equals("Unborrowed")) {
                System.out.println("Book Name: " + book.getBookName() + "; " +
                        "Author Name: " + book.getAuthor() + "; Publish Year: " + book.getYear());
                System.out.println();
            }
        }
        System.out.println("============================================");

        System.out.println("Enter the book name you want to borrow: ");
        String bookName = input.nextLine();
        bookName = name(bookName);
        while (bookName != null) {
            for (Book book:bookList) {
                if (book.getBookName().equalsIgnoreCase(bookName)) {
                    Manegement.issueBook(book);
                    System.out.println("Book issued successfully!");
                    continueIssue();
                }
            }
            if ((bookName.equalsIgnoreCase("Quit") || bookName.equalsIgnoreCase("q") || bookName.equalsIgnoreCase("[quit]"))) {
                launch();
            }
            System.out.println("The Book is not in the book list!");
            System.out.println("Please Enter the book name again or Enter [Quit] to quit: ");
            bookName = input.nextLine();
            bookName = name(bookName);
        }
        System.out.println("============================================");
        continueIssue();
    }

    //This method will ask user if he/she wants to continue to issue the books
    private static void continueIssue() {
        System.out.println("Do you want to borrow more books? Yes/No");
        String Input = input.nextLine();
        if (Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y")) {
            IssueBook();
        } else if (Input.equalsIgnoreCase("No") || Input.equalsIgnoreCase("N")) {
            launch();
        } else {
            continueIssue();
        }
    }

    //This method will return the book
    private static void returnBook() {
        System.out.println("============================================");
        System.out.println("Here is the books that are borrowed: ");
        System.out.println();
        LinkedList<Book> bookList = Manegement.getBookList();
        for (Book book:bookList) {
            if (book.getStatus().equals("Borrowed")) {
                System.out.println("Book Name: " + book.getBookName() + "; " +
                        "Author Name: " + book.getAuthor() + "; Publish Year: " + book.getYear());
                System.out.println();
            }
        }

        System.out.println("Enter the book name you want to return: ");
        String bookName = input.nextLine();
        bookName = name(bookName);
        while (bookName != null) {
            for (Book book:bookList) {
                if (book.getBookName().equalsIgnoreCase(bookName)) {
                    Manegement.returnBook(book);
                    System.out.println("Book returned successfully!");
                    continueReturn();
                }
            }
            if ((bookName.equalsIgnoreCase("Quit") || bookName.equalsIgnoreCase("q") || bookName.equalsIgnoreCase("[quit]"))) {
                launch();
            }
            System.out.println("The Book is not in the book list!");
            System.out.println("Please Enter the book name again or Enter [Quit] to quit: ");
            bookName = input.nextLine();
            bookName = name(bookName);
        }
        System.out.println("============================================");
        continueReturn();

    }

    //This method will ask user if he/she wants to continue to return the book
    private static void continueReturn() {
        System.out.println("Do you want to continue? Yes/No");
        String Input = input.nextLine();
        if (Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y")) {
            returnBook();
        } else if (Input.equalsIgnoreCase("No") || Input.equalsIgnoreCase("N")) {
            launch();
        } else {
            continueReturn();
        }
    }

    //This method will check the status of the book
    private static void checkStatus() {
        System.out.println("============================================");
        System.out.println("Here is the book list: ");
        System.out.println();
        LinkedList<Book> bookList = Manegement.getBookList();
        for (Book book:bookList) {
                System.out.println("Book Name: " + book.getBookName() + "; " +
                        "Author Name: " + book.getAuthor() + "; Publish Year: " + book.getYear()
                        + "; Book Status: " + book.getStatus());
                System.out.println();
        }
        System.out.println("============================================");
        continueIfCheck();
    }


    //This method will ask user if he/she wants to continue to check
    //the status of the book list
    private static void continueIfCheck() {
        System.out.println("Do you want to continue? Yes/No");
        String Input = input.nextLine();
        if (Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y")) {
            checkStatus();
        } else if (Input.equalsIgnoreCase("No") || Input.equalsIgnoreCase("N")) {
            launch();
        } else {
            continueIfCheck();
        }
    }

    //This method will show the information of application
    private static void help() {
        System.out.println("========Description of Application===========");
        System.out.println("This app is for library managers to store");
        System.out.println("new books, issue books and return books. ");
        System.out.println("They can also look for all in-stock books");
        System.out.println("information and current status. ");
        System.out.println("-------------Author---------------------------");
        System.out.println("Huang, Ruoran");
        launch();
    }

    //This method will quit the application
    private static void quit() {
        System.out.println("==========================================");
        System.out.println("Thank you for using Library Manege System!");
        Runtime.getRuntime().exit(0);
    }

}
