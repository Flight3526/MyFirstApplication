package com.jnu.student;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BookViewHolder>{
    private final List<BookItem> booklist;
    public MyAdapter(List<BookItem> booklist){
        this.booklist = booklist;
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        ImageView image;
        public BookViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.text_view_book_title);
            price = itemView.findViewById(R.id.text_view_book_price);
            image = itemView.findViewById(R.id.image_view_book_cover);
        }
    }

    @NonNull
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    public void onBindViewHolder(BookViewHolder holder, int position){
        BookItem book = booklist.get(position);
        holder.name.setText(book.getTitle());
        holder.price.setText(book.getPrice());
        holder.image.setImageResource(book.getCoverResourceId());
    }
    public int getItemCount(){
        return booklist.size();
    }
}
