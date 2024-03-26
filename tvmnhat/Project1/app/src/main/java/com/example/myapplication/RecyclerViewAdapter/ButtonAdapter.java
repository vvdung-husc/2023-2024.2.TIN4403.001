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
import com.example.application.R;
import com.example.myapplication.Object.Button;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonsViewHolder>{
    private Context mcontexts;
    private List<Button> mbuttons;

    public ButtonAdapter(Context mcontexts){
        this.mcontexts = mcontexts;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Button> list) {
        this.mbuttons = list;
        notifyDataSetChanged();
    }
    @NonNull
    @NotNull
    @Override
    public ButtonsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.button_home_noibat, viewGroup,
                false);
        return new ButtonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ButtonsViewHolder buttonsViewHolder, int i) {
        Button button = mbuttons.get(i);
        if(button == null){
            return;
        }

        buttonsViewHolder.imageView.setImageResource(button.getResourseId());
        buttonsViewHolder.textView.setText(button.getTitle());

    }

    @Override
    public int getItemCount() {
        if(mbuttons != null){
            return mbuttons.size();
        }
        return 0;
    }

    public static class ButtonsViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView textView;

        public ButtonsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.btn_icon);
            textView = itemView.findViewById(R.id.home_btn_title);
        }
    }
}
