package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rcvfood;
    private Button btn_cafe, btn_milktea, btn_yogust;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cafe = findViewById(R.id.btn_cafe);
        btn_milktea = findViewById(R.id.btn_milktea);
        btn_yogust = findViewById(R.id.btn_yogust);

        rcvfood = findViewById(R.id.rcv_food);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rcvfood.setLayoutManager(gridLayoutManager);

        FoodAdapter foodAdapter = new FoodAdapter(getListFood());
        rcvfood.setAdapter(foodAdapter);

        btn_cafe.setOnClickListener(this);
        btn_milktea.setOnClickListener(this);
        btn_yogust.setOnClickListener(this);
    }

    public List<Food> getListFood(){
        List<Food> listFood = new ArrayList<>();
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));
        listFood.add(new Food(R.drawable.cafe, "Cafe", Food.TYPE_CAFE));

        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));
        listFood.add(new Food(R.drawable.trasua, "Milk Tea", Food.TYPE_MILKTEA));

        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));
        listFood.add(new Food(R.drawable.suachua, "Yogust", Food.TYPE_YOGUST));

        return listFood;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cafe) {
            scrolltoItem(0);
        } else if (view.getId() == R.id.btn_milktea) {
            scrolltoItem(10);
        } else if (view.getId() == R.id.btn_yogust) {
            scrolltoItem(20);
        }
    }

    public void scrolltoItem(int index){
        if(gridLayoutManager == null){
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(index, 0);
    }
}