package com.amulyakhare.td.sample.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:49 PM
 */
public class DataSource {

    public static final int NO_NAVIGATION = -1;

    private ArrayList<DataItem> mDataSource;
    private DrawableProvider mProvider;

    public DataSource(Context context) {
        mProvider = new DrawableProvider(context);
        mDataSource = new ArrayList<DataItem>();
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_MULTIPLE_LETTERS,
                "sdfsdfsdfs"));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_FONT, "吴彦祖"));
    }

    public int getCount() {
        return mDataSource.size();
    }

    public DataItem getItem(int position) {
        return mDataSource.get(position);
    }

    private DataItem itemFromType(int type, String text) {
        String label = null;
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_MULTIPLE_LETTERS:
                label = "Support multiple letters";
                drawable = mProvider.getRectWithMultiLetter(text);
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_FONT:
                label = "Support variable font styles";
                drawable = mProvider.getRoundWithCustomFont(text);
                type = NO_NAVIGATION;
                break;
        }
        return new DataItem(label, drawable, type);
    }
}
