package com.example.android.booklistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageLinks implements Parcelable {


    @SerializedName("smallThumbnail")
    public String smallThumbnail;
    @SerializedName("thumbnail")
    public String thumbnail;
    public final static Parcelable.Creator<ImageLinks> CREATOR = new Creator<ImageLinks>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ImageLinks createFromParcel(Parcel in) {
            return new ImageLinks(in);
        }

        public ImageLinks[] newArray(int size) {
            return (new ImageLinks[size]);
        }

    }
            ;


    protected ImageLinks(Parcel in) {
        this.smallThumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ImageLinks() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(smallThumbnail);
        dest.writeValue(thumbnail);
    }

    public int describeContents() {
        return 0;
    }

}