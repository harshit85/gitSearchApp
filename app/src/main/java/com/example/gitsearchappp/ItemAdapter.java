package com.example.gitsearchappp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<ItemLayout> mItems;
    private SqliteHelper sqliteHelper;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textDescription;
        public TextView textLanguage;
        public ImageButton imageButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.header);
            textDescription = itemView.findViewById(R.id.description);
            textLanguage = itemView.findViewById(R.id.language);
            imageButton = itemView.findViewById(R.id.btn_favorite);
        }
    }

    public ItemAdapter(ArrayList<ItemLayout> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
        ItemViewHolder iv = new ItemViewHolder(v);
        return iv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final ItemLayout itemLayout = mItems.get(position);
        holder.textName.setText(itemLayout.getmRepositoryName());
        holder.textDescription.setText(itemLayout.getmDescription());
        holder.textLanguage.setText(itemLayout.getmLanguage());
        insertAndSetFavourite(holder, itemLayout);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void insertAndSetFavourite(final ItemViewHolder holder, final ItemLayout itemLayout) {
        Context context = holder.imageButton.getContext();
        sqliteHelper = new SqliteHelper(context);
        Cursor res = sqliteHelper.showFavourite();
        String color = "white";
        while (res.moveToNext()) {
            if (parseInt(res.getString(0)) == itemLayout.getmId()) {
                color = "red";
                break;
            } else {
                color = "white";
            }
        }
        if (color == "white") {
            holder.imageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = sqliteHelper.showFavourite();
                boolean isInser = true;
                if (res.getCount() == 0) {
                    sqliteHelper.addFavouriteDirectory(itemLayout.getmId(), itemLayout.getmRepositoryName()
                            , itemLayout.getmLanguage(), itemLayout.getmDescription());
                    holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    while (res.moveToNext()) {
                        if (parseInt(res.getString(0)) == itemLayout.getmId()) {
                            isInser = false;
                            break;
                        } else {
                            isInser = true;
                        }
                    }
                    if (isInser) {
                        sqliteHelper.addFavouriteDirectory(itemLayout.getmId(), itemLayout.getmRepositoryName()
                                , itemLayout.getmLanguage(), itemLayout.getmDescription());
                        holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        sqliteHelper.deleteFavourite(itemLayout.getmId());
                        holder.imageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                }

            }
        });
    }

}
