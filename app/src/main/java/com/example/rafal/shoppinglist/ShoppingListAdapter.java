package com.example.rafal.shoppinglist;

/**
 * Created by rafal on 24.03.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private List<Item> shoppingListItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public ShoppingListAdapter(List<Item> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = shoppingListItems.get(position);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingListItems.size();
    }
}