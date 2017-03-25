package com.example.rafal.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private ShoppingListAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private List<Item> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        myAdapter = new ShoppingListAdapter(shoppingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        myRecyclerView.setAdapter(myAdapter);

        prepareMovieData();

    }

    private void prepareMovieData() {
        Item item = new Item("proszek");
        shoppingList.add(item);

        item = new Item("pasta");
        shoppingList.add(item);

        item = new Item("woda");
        shoppingList.add(item);

        myAdapter.notifyDataSetChanged();
    }


}

