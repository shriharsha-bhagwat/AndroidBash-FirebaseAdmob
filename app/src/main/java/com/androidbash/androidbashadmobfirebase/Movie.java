package com.androidbash.androidbashadmobfirebase;

import android.support.annotation.DrawableRes;


public class Movie {
    private String mName;
    private int likes;
    @DrawableRes
    private int mImage;


    public Movie(String name, int likes,
                 @DrawableRes int mImage) {
        this.mName = name;
        this.likes = likes;
        this.mImage = mImage;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

}
