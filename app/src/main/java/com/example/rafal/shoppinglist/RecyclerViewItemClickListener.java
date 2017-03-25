package com.example.rafal.shoppinglist;

import android.view.View;

/**
 * Created by rafal on 25.03.2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}