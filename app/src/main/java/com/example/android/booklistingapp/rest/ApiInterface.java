package com.example.android.booklistingapp.rest;

import com.example.android.booklistingapp.model.Item;
import com.example.android.booklistingapp.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("volumes")
    Call<ItemResponse> getItems(@Query("q") String q, @Query("maxResults") int maxResults);
/*
    @GET("en/collection")
    Call<ArtObjectResponse> getPaintings(@Query("key") String apiKey, @Query("format") String format, @Query("ps") int resultsPerPage, @Query("imgonly") boolean imgOnly, @Query("type") String type, @Query("s") String sortBy);

    @GET("en/collection/{objectNumber}")
    Call<ArtObjectDetailResponse> getArtObjectDetails(@Path("objectNumber") String objectNumber, @Query("key") String apiKey, @Query("format") String format);
*/

}