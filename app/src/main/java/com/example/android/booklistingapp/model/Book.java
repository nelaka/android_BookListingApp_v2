package com.example.android.booklistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Book implements Parcelable {


    @SerializedName("title")
        private String title;
        @SerializedName("authors")
        private ArrayList authors = null;
        @SerializedName("infoLink")
        private String infoLink;
        @SerializedName("subtitle")
        private String subtitle;
        @SerializedName("imageLinks")
        private ImageLinks imageLink;

        public final static Parcelable.Creator<Book> CREATOR = new Creator<Book>() {

            public Book createFromParcel(Parcel in) {
                return new Book(in);
            }

            public Book[] newArray(int size) {
                return (new Book[size]);
            }

        };

    Book(Parcel in) {
            this.title = in.readString();
            this.authors = in.readArrayList(String.class.getClassLoader());
            this.infoLink = in.readString();
            this.subtitle = in.readString();
            this.imageLink = (ImageLinks) in.readValue(ImageLinks.class.getClassLoader());

        }

        /**
         *
         * @param infoLink book's link to google books page
         * @param authors book's authors
         * @param title book's title
         * @param subtitle book's subtitle
         * @param thumbnail book's small cover image
         * */
        public Book(String title, String subtitle, ArrayList<String> authors, String thumbnail, String infoLink) {
            super();
            this.title = title;
            this.authors = authors;
            this.infoLink = infoLink;
            this.imageLink.smallThumbnail = thumbnail;
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Book withTitle(String title) {
            this.title = title;
            return this;
        }

        public ArrayList getAuthors() {
            return authors;
        }

    public Book withAuthors(ArrayList<String> authors) {
            this.authors = authors;
            return this;
        }


        public String getInfoLink() {
            return infoLink;
        }

        public void setInfoLink(String infoLink) {
            this.infoLink = infoLink;
        }

        public Book withInfoLink(String infoLink) {
            this.infoLink = infoLink;
            return this;
        }

    public String getThumbnail() {

            if (imageLink != null) return imageLink.smallThumbnail;
            else return null;
    }

    public void setThumbnail(String thumbnail) {
        this.imageLink.smallThumbnail = thumbnail;
    }

    public Book withThumbnail(String thumbnail) {
        this.imageLink.smallThumbnail = thumbnail;
        return this;
    }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Book withSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeList(authors);
            dest.writeString(infoLink);
            dest.writeString(subtitle);
        }

        public int describeContents() {
            return 0;
        }

    }
