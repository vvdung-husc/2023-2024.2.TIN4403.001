package com.example.myapplication.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Object.Product;
import com.example.myapplication.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Product_Featured_Adapter extends RecyclerView.Adapter<Product_Featured_Adapter.ProductViewHolder>{
    private Context context;
    private List<Product> mProducts;

    public Product_Featured_Adapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Product> list){
        this.mProducts = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup,
                false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder productViewHolder, int i) {
         Product product = mProducts.get(i);
         if(product == null){
             return;
         }

         productViewHolder.imgview.setImageResource(product.getResourseId());
         productViewHolder.textView.setText(product.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mProducts != null){
            return mProducts.size();
        }
        return 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgview;
        private final TextView textView;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.img_product);
            textView = itemView.findViewById(R.id.product_name);
        }
    }
}
