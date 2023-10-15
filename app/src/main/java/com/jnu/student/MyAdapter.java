package com.jnu.student;

import android.view.ContextMenu;
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
    public static class BookViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name;
        TextView price;
        ImageView image;
        public BookViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.text_view_book_title);
            price = itemView.findViewById(R.id.text_view_book_price);
            image = itemView.findViewById(R.id.image_view_book_cover);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.setHeaderTitle("具体操作");
            menu.add(0,0, this.getAdapterPosition(),"添加");
            menu.add(0,1, this.getAdapterPosition(),"删除");
            menu.add(0,2, this.getAdapterPosition(),"修改");
        }
    }
}



class BookItem {
    private final String book_name;
    private final String book_price;
    private final int book_image;
    public String getTitle() {
        return book_name;
    }
    public String getPrice() {
        return book_price;
    }
    public int getCoverResourceId() {
        return book_image;
    }
    BookItem(String name, String price, int image){
        book_name = name;
        book_price = price;
        book_image = image;
    }
}
