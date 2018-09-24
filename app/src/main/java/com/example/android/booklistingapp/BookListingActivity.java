package com.example.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.android.booklistingapp.adapter.BookListingAdapter;
import com.example.android.booklistingapp.model.Book;
import com.example.android.booklistingapp.model.Item;
import com.example.android.booklistingapp.model.ItemResponse;
import com.example.android.booklistingapp.rest.ApiClient;
import com.example.android.booklistingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.booklistingapp.Config.maxResults;

public class BookListingActivity extends AppCompatActivity {
    private static final String TAG = BookListingActivity.class.getSimpleName();

    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.search_button)
    Button mSearchButton;
    @BindView(R.id.search)
    EditText mSearchEditTextView;
    @BindView(R.id.books_rv)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private List<Item> mItems = new ArrayList<>() ;
private List<Book> mBooks =new ArrayList<>();

    /**
     * Adapter for the list of books
     */
    private BookListingAdapter mAdapter;

    /**
     * Returns true if network is available or about to become available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager( getApplicationContext(), LinearLayout.VERTICAL, false);
        /* Association of the LayoutManager with the RecyclerView */
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*
         * Setting to improve performance when changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);


        // Set empty view on the ListView, so that it only shows when the list has 0 items.
     //   mBookLayout.setEmptyView(mEmptyView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookListingAdapter(this);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        //mRecyclerView.setAdapter(mAdapter);

        // Set a click listener on the search Button, to implement the search

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the button is clicked on.
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                mItems = null;
                //Check for internet connection
                if (isNetworkAvailable(context)) {
                    String searchTerm = mSearchEditTextView.getText().toString();


                    if (searchTerm.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nothing to search for... :(", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Searching for: " + searchTerm, Toast.LENGTH_SHORT).show();
                        getBooks(searchTerm);

                    }

                } else {
                    //Provide feedback about no internet connection
                    Toast.makeText(BookListingActivity.this, "Please check your internet connection - No internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
    /*    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                Book currentBook = mAdapter.getItem(position).getBook();

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getInfoLink());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });*/
    }
public void getBooks(String searchTerm) {
 //   if (mItems.size() < 1 || mItems.get(0) == null) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemResponse> call;
        call = apiService.getItems(searchTerm, maxResults);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.body() != null) {
                    mItems = response.body().getItems();
                }
                Log.d("MAIN ", "Number of results received: " + mItems.size());
                //  mLoadingIndicator.setVisibility(View.INVISIBLE);

                // If there is a valid list of {@link Book}s, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (mItems != null && !mItems.isEmpty()) {
                    for (int i=0; i<mItems.size(); i++)
                        mBooks.add(mItems.get(i).getBook());
                    mEmptyView.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mAdapter.setData(mBooks);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                //     mLoadingIndicator.setVisibility(View.INVISIBLE);
                //   mErrorMsg = getString(R.string.msg_error);
                // showErrorMessage(mErrorMsg);
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
//    }

}
}



