package com.example.myapplication.Fragment;

import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.application.R;
import com.example.myapplication.Object.Button;
import com.example.myapplication.Object.Product;

import com.example.myapplication.RecyclerViewAdapter.ButtonAdapter;
import com.example.myapplication.RecyclerViewAdapter.Product_Featured_Adapter;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private RecyclerView recyclerView_button;
    private RecyclerView recyclerView_product;
    private ButtonAdapter buttonAdapter;
    private Product_Featured_Adapter productAdapter;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fraggment_home_page);

        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        recyclerView_button = findViewById(R.id.home_btn_noibat);
        buttonAdapter = new ButtonAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,
                false);
        buttonAdapter.setData(getListButton());
        recyclerView_button.setLayoutManager(linearLayoutManager);
        recyclerView_button.setAdapter(buttonAdapter);

        recyclerView_product = findViewById(R.id.home_product_noibat);
        productAdapter = new Product_Featured_Adapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,
                false);
        productAdapter.setData(getListProduct());
        recyclerView_product.setLayoutManager(linearLayoutManager1);
        recyclerView_product.setAdapter(productAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView_product = findViewById(R.id.home_product_yeuthich);
        recyclerView_product.setAdapter(productAdapter);
        recyclerView_product.setLayoutManager(gridLayoutManager);
    }

    public List<Button> getListButton(){
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(new Button(R.drawable.baseline_search_24, "All"));
        buttonList.add(new Button(R.drawable.baseline_search_24, "Medicine"));
        buttonList.add(new Button(R.drawable.baseline_search_24, "Consumer"));
        buttonList.add(new Button(R.drawable.baseline_search_24, "Food"));
        buttonList.add(new Button(R.drawable.baseline_search_24, "Fashion"));

        return buttonList;
    }

    public List<Product> getListProduct(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(R.drawable.gaoquelam, "Gao Que Lam", "30$"));
        productList.add(new Product(R.drawable.khuondaubaominh, "Khuong dau Bao Minh", "30$"));
        productList.add(new Product(R.drawable.thitheoquelam, "Thit heo Que Lam", "30$"));
        productList.add(new Product(R.drawable.mangcutquelam, "Mang cut Que Lam", "30$"));
        productList.add(new Product(R.drawable.gaosenhong, "Gao Sen Hong", "30$"));
        productList.add(new Product(R.drawable.thitgaquelam, "Thit ga Que Lam", "30$"));

        return productList;
    }
}