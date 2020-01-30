package com.example.gitsearchappp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SqliteHelper sqliteHelper;
    ArrayList<ItemLayout> itemLayouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRecyclerView =findViewById(R.id.favoriteDirectory);
        mLayoutManager = new LinearLayoutManager(this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemLayouts =new ArrayList<>();
        sqliteHelper = new SqliteHelper(this);
        Cursor res = sqliteHelper.showFavourite();
        while (res.moveToNext())
        {
            itemLayouts.add(new ItemLayout(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),res.getString(3)));
        }
        mAdapter = new favouriteAdapter(itemLayouts);
        mRecyclerView.setAdapter(mAdapter);
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            ItemLayout itemLayout = itemLayouts.get(viewHolder.getAdapterPosition());
            sqliteHelper.deleteFavourite(itemLayout.getmId());
            itemLayouts.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }
    };
}
