package com.example.android.booklistingapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.android.booklistingapp.adapter.BookListingAdapter;
import com.example.android.booklistingapp.model.Book;
import com.example.android.booklistingapp.model.Item;
import com.example.android.booklistingapp.model.ItemResponse;
import com.example.android.booklistingapp.rest.ApiClient;
import com.example.android.booklistingapp.rest.ApiInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.booklistingapp.Config.maxResults;

public class BookListingActivity extends AppCompatActivity implements BookListingAdapter.BookListingAdapterOnClickHandler {
    private static final String TAG = BookListingActivity.class.getSimpleName();

    @BindView(R.id.empty_view)
    View mEmptyView;
     @BindView(R.id.books_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView((R.id.adView))
    AdView mAdView;
    private ArrayList<Item> mItems = new ArrayList<>();

    /**
     * Adapter for the list of books
     */
    private BookListingAdapter mAdapter;

    /**
     * Returns true if network is available or about to become available
     */
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);
        ButterKnife.bind(this);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        /* Association of the LayoutManager with the RecyclerView */
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*
         * Setting to improve performance when changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookListingAdapter(this, this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Context context = getApplicationContext();

            //Check for internet connection
            if (isNetworkAvailable(context)) {
                String searchTerm = intent.getStringExtra(SearchManager.QUERY);
                Toast.makeText(getApplicationContext(), "Searching for: " + searchTerm, Toast.LENGTH_SHORT).show();
                getBooks(searchTerm);
            } else {
                //Provide feedback about no internet connection
                Toast.makeText(BookListingActivity.this, "No internet connection available. Please check your internet connection!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getBooks(query);
        }
    }

    private void getBooks(String searchTerm) {

        final ArrayList<Book> mBooks = new ArrayList<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemResponse> call;
        call = apiService.getItems(searchTerm, maxResults);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                if (response.body() != null ) mItems = response.body().getItems();
              //  Log.d("MAIN ", "Number of results received: " + mItems.size());
                mLoadingIndicator.setVisibility(View.INVISIBLE);

                // If there is a valid list of {@link Book}s, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (mItems != null && !mItems.isEmpty()) {
                    for (int i = 0; i < mItems.size(); i++)
                        mBooks.add(mItems.get(i).getBook());
                    mEmptyView.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mAdapter.setData(mBooks);
                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    mRecyclerView.setAdapter(mAdapter);

                    //Load an app
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mEmptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Unfortunately, no results. Try with another term...", Toast.LENGTH_SHORT).show();
                //   mErrorMsg = getString(R.string.msg_error);
                // showErrorMessage(mErrorMsg);
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    @Override
    public void onClick(Book book) {
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri bookUri = Uri.parse(book.getInfoLink());

        // Create a new intent to view the book URI
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

        // Send the intent to launch a new activity
        startActivity(websiteIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

}



