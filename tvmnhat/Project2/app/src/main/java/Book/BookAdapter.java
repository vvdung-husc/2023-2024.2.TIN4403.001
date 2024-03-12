package Book;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project2.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> mbooks;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> list){
        this.mbooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup,
                false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookViewHolder bookViewHolder, int i) {
        Book book = mbooks.get(i);
        if(book == null){
            return;
        }

        bookViewHolder.imageView.setImageResource(book.getResourseId());
        bookViewHolder.textView.setText(book.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mbooks != null){
            return mbooks.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView textView;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_book);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
