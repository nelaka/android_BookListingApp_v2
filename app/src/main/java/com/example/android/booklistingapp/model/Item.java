package com.example.android.booklistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

    @SerializedName("id")
    private String mId;
        @SerializedName("volumeInfo")
        private Book mVolumeInfo;

    public final static Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
           @SuppressWarnings({ "unchecked" })
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }
            public Item[] newArray(int size) {
                return (new Item[size]);
            }

        };

        protected Item(Parcel in) {
            mId = in.readString();
            mVolumeInfo = (Book) in.readValue(Book.class.getClassLoader());
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Item() {
        }

        /**
         *
         * @param volumeInfo
         */
        public Item( Book volumeInfo) {
            super();
            mVolumeInfo = volumeInfo;

        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }


        public Book getBook() {
            return mVolumeInfo;
        }

        public void setBook(Book volumeInfo) {
            mVolumeInfo = volumeInfo;
        }

        public Item withBook(Book volumeInfo) {
            mVolumeInfo = volumeInfo;
            return this;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeValue(mVolumeInfo);

        }

        public int describeContents() {
            return 0;
        }

    }