package Category;

import Book.BookAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project2.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final Context mcontexts;
    private List<Category> mListCategory;

    public CategoryAdapter(Context mcontexts) {
        this.mcontexts = mcontexts;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup,
                false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder categoryViewHolder, int i) {
        Category category = mListCategory.get(i);
        if(category == null){
            return;
        }

        categoryViewHolder.tvNameCategory.setText(category.getName_category());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontexts, RecyclerView.HORIZONTAL,
                false);
        categoryViewHolder.rcvbooks.setLayoutManager(linearLayoutManager);

        BookAdapter bookAdapter = new BookAdapter();
        bookAdapter.setData(category.getBooks());
        categoryViewHolder.rcvbooks.setAdapter(bookAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNameCategory;
        private final RecyclerView rcvbooks;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            rcvbooks = itemView.findViewById(R.id.rcv_book);
        }
    }
}
