package com.fourreau.itwapp.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Pierre on 04/05/2015.
 */
public class ListViewInterviewItem {
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public ListViewInterviewItem(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
