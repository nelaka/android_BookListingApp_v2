package com.example.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

<<<<<<< HEAD
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

=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> master
public class BookListingActivity extends AppCompatActivity {
    private static final String TAG = BookListingActivity.class.getSimpleName();

<<<<<<< HEAD
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
=======
    /**
     * URL for google books data from the Google API
     */
    private static final String GBOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";

>>>>>>> master
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
<<<<<<< HEAD
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
=======

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);
>>>>>>> master

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookListingAdapter(this);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
<<<<<<< HEAD
        //mRecyclerView.setAdapter(mAdapter);
=======
        bookListView.setAdapter(mAdapter);
>>>>>>> master

        // Set a click listener on the search Button, to implement the search
        Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the button is clicked on.
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                mItems = null;
                //Check for internet connection
                if (isNetworkAvailable(context)) {
<<<<<<< HEAD
                    String searchTerm = mSearchEditTextView.getText().toString();
=======

                    EditText searchEditTextView = (EditText) findViewById(R.id.search);
                    String searchTerm = searchEditTextView.getText().toString();
>>>>>>> master

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
<<<<<<< HEAD
        /*mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
=======
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
>>>>>>> master
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

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of books in the response.
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return a Book.
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
  /*  private class BookAsyncTask extends AsyncTask<String, Void, Item> {
        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Book}s as the result.
         */
   /*     ProgressDialog progDailog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(BookListingActivity.this);
            progDailog.setMessage("Searching...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Item doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null

         //   List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return mBooks;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of book data from a previous
         * query to Google Books API. Then we update the adapter with the new list of books,
         * which will trigger the ListView to re-populate its list items.
         */
  /*      @Override
        protected void onPostExecute(Item data) {
            // Clear the adapter of previous book data
            mAdapter.clear();
            progDailog.dismiss();
            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);


            }
        }
    }
}
*/

