package com.example.project2;

import Book.Book;
import Category.Category;
import Category.CategoryAdapter;
import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvCategory = findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }

    public List<Category> getListCategory(){
        List<Category> listCategory = new ArrayList<>();

        List<Book> listBook = new ArrayList<>();
        listBook.add(new Book(R.drawable.dacnhantam, "Book 1"));
        listBook.add(new Book(R.drawable.dacnhantam, "Book 2"));
        listBook.add(new Book(R.drawable.dacnhantam, "Book 3"));
        listBook.add(new Book(R.drawable.dacnhantam, "Book 4"));
        listBook.add(new Book(R.drawable.dacnhantam, "Book 5"));

        listCategory.add(new Category("Catelogy 1", listBook));
        listCategory.add(new Category("Catelogy 2", listBook));
        listCategory.add(new Category("Catelogy 3", listBook));
        listCategory.add(new Category("Catelogy 4", listBook));
        listCategory.add(new Category("Catelogy 5", listBook));

        return  listCategory;
    }
}