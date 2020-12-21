package com.example.easy;

import android.media.Image;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class categoryModel {


    public categoryModel(){}

    public TextView getDescription() { return description; }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public CircularImageView getPoster() {
        return poster;
    }

    public void setPoster(CircularImageView poster) {
        this.poster = poster;
    }

    TextView description;
    CircularImageView poster ;


}


