package com.example.android.booklistingapp;

import java.util.ArrayList;


public class Utils {
    public static String fromListToString(ArrayList<String> stringList){
        if (stringList!=null && stringList.size()>0) {
            // We need a String with all authors divided with comma
            StringBuilder bigString = new StringBuilder();

            // For each author in the authorArray, append its value to authors StringBuilder
            for (int j = 0; j < stringList.size(); j++) {
                bigString.append(stringList.get(j)).append(", ");
            }
            //remove comma from the end of the string
            bigString.setLength(bigString.length() - 2);
            return bigString.toString();
        }
        else return null;
    }
}
