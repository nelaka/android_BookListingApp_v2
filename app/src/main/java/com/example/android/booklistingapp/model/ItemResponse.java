package com.example.android.booklistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemResponse implements Parcelable
{

    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("items")
    private ArrayList<Item> mItems;
    public final static Parcelable.Creator<ItemResponse> CREATOR = new Creator<ItemResponse>() {
        public ItemResponse createFromParcel(Parcel in) {
            return new ItemResponse(in);
        }

        public ItemResponse[] newArray(int size) {
            return new ItemResponse[size];
        }
    };

    private ItemResponse(Parcel in) {
        this.totalItems = in.readInt();
        in.readList(mItems, Item.class.getClassLoader());
    }


    /**
     *
     * @param items
     * @param totalItems
     */
    public ItemResponse(int totalItems, ArrayList<Item> items) {
        super();
        this.totalItems = totalItems;
        mItems = items;
    }


    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ItemResponse withTotalItems(int totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<Item> items) {
        mItems = items;
    }

    public ItemResponse withItems(ArrayList<Item> items) {
        mItems = items;
        return this;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalItems);
        dest.writeList(mItems);
    }

    public int describeContents() {
        return 0;
    }

}