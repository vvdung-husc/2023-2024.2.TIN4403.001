package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private final List<Food> mListFood;

    public FoodAdapter(List<Food> mListFood) {
        this.mListFood = mListFood;
    }
    @NonNull
    @NotNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup,
                false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FoodViewHolder foodViewHolder, int i) {
        Food food = mListFood.get(i);
        if(food == null){
            return;
        }

        foodViewHolder.imageView.setImageResource(food.getImage());
        foodViewHolder.textView.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        if(mListFood != null){
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;

        public FoodViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_food);
            textView = itemView.findViewById(R.id.tv_name_food);
        }
    }
}
