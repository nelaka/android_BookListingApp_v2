package com.example.android.booklistingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.booklistingapp.R;
import com.example.android.booklistingapp.Utils;
import com.example.android.booklistingapp.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 * {@link BookListingAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Book} objects.
 * */
public class BookListingAdapter extends RecyclerView.Adapter<BookListingAdapter.BookViewHolder> {
    private final Context mContext;
    private List<Book> mBooks = new ArrayList<>();

    public BookListingAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false);
        view.setFocusable(true);
        return new BookViewHolder(view, mBooks);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindBooks(position);
    }

    @Override
    public int getItemCount() {
        if (null == mBooks) return 0;
        return mBooks.size();
    }

    public void setData(List<Book> data) {
        mBooks = data;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = BookViewHolder.class.getSimpleName();
        @BindView(R.id.title)
        TextView titleTextView;
        @BindView(R.id.subtitle)
        TextView subtitleTextView;
        @BindView(R.id.authors)
        TextView authorsTextView;
        private List<Book> mBooks;


        public BookViewHolder(View itemView, List<Book> books) {
            super(itemView);
            mBooks = books;
            ButterKnife.bind(this, itemView);
        }

        public void bindBooks(int position) {
            // Get the {@link Book} object located at this position in the list
            Book currentBook = mBooks.get(position);

            titleTextView.setText(currentBook.getTitle());

            // Find the TextView with view ID subtitle and hide it, if it is empty
            if (currentBook.getSubtitle() == null) subtitleTextView.setVisibility(View.GONE);
            else {
                subtitleTextView.setText(currentBook.getSubtitle());
                subtitleTextView.setVisibility(View.VISIBLE);
            }

            // Find the TextView with view ID authors and hide it, if it is empty
            if (currentBook.getAuthors().isEmpty()) authorsTextView.setVisibility(View.GONE);
            else {
                String authors = Utils.fromListToString(currentBook.getAuthors());
                authorsTextView.setText(authors);
                authorsTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
