package com.example.project2;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText edit_id, edit_book_title, edit_book_author, edit_book_pages;
    Button btnAddData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        edit_id = findViewById(R.id.edit_id);
        edit_book_title = findViewById(R.id.edit_title);
        edit_book_author = findViewById(R.id.edit_author);
        edit_book_pages = findViewById(R.id.edit_pages);
        btnAddData = findViewById(R.id.button);
        AddData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(edit_id.getText().toString(), edit_book_title.getText().toString(),
                        edit_book_author.getText().toString(), edit_book_pages.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}