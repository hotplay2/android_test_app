package com.example.rafal.shoppinglist;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {
    private RecyclerView myRecyclerView;
    private ShoppingListAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    DBHelper db;

    private List<Item> shoppingList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        db = new DBHelper(this);

        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        myAdapter = new ShoppingListAdapter(shoppingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        myRecyclerView.setItemAnimator(itemAnimator);
        myRecyclerView.setAdapter(myAdapter);
        myRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, myRecyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d("TEST", shoppingList.get(position).getName());
                showEditDialog(shoppingList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("TEST", shoppingList.get(position).getName());
            }
        }));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                myAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                db.deleteItem(shoppingList.get(viewHolder.getAdapterPosition()));
                myAdapter.onItemDismiss(viewHolder.getAdapterPosition());
                myAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(myRecyclerView);
        prepareMovieData();
    }

    private void prepareMovieData() {
        List<Item> asd = db.getAllItems();
        if (asd.size() <3){
            db = new DBHelper(this);

            Item item = new Item("proszek");
            db.addItem(item);

            item = new Item("pasta");
            db.addItem(item);

            item = new Item("woda");
            db.addItem(item);

            asd = db.getAllItems();

        }
        for(int i=0; i<asd.size(); i++){
            shoppingList.add(asd.get(i));
        }

        myAdapter.notifyDataSetChanged();
    }

    private void showEditDialog(Item item, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance(item, position);
        editNameDialogFragment.show(fm, "");
    }

    @Override
    public void onFinishEditDialog(String inputText, int position) {
        if (inputText.equals("")) {
            shoppingList.remove(position);
            db.deleteItem(shoppingList.get(position));
        }
        else {
            shoppingList.get(position).setName(inputText);
            db.updateItem(shoppingList.get(position));
            myAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                addItem();
                return true;
            case R.id.action_settings:
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(){
        Item item = new Item("");
        item.setId(shoppingList.size()+1);
        shoppingList.add(item);
        db.addItem(item);
        int position = shoppingList.indexOf(item);
        showEditDialog(item, position);
    }
}

