package Category;

import Book.Book;

import java.util.List;

public class Category {
    private String name_category;
    private List<Book> books;

    public Category(String name_category, List<Book> books) {
        this.name_category = name_category;
        this.books = books;

    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
