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
        }

        /**
         *
         * @param infoLink
         * @param authors
         * @param title
         * @param subtitle
         * */
        public Book(String title, String subtitle, ArrayList<String> authors,  String infoLink) {
            super();
            this.title = title;
            this.authors = authors;
            this.infoLink = infoLink;
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
