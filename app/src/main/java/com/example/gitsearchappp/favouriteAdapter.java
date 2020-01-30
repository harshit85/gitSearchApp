package com.example.gitsearchappp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class favouriteAdapter extends RecyclerView.Adapter <favouriteAdapter.ItemViewHolder>{

    ArrayList<ItemLayout> mItems;
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
    public favouriteAdapter(ArrayList<ItemLayout> items) {
        mItems = items;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
        favouriteAdapter.ItemViewHolder iv = new favouriteAdapter.ItemViewHolder(v);
        return iv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final ItemLayout itemLayout = mItems.get(position);
        holder.textName.setText(itemLayout.getmRepositoryName());
        holder.textDescription.setText(itemLayout.getmDescription());
        holder.textLanguage.setText(itemLayout.getmLanguage());
        holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
