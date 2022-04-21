package com.discometro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolderItems> {

    ArrayList<String> listItems;
    public FavoritosAdapter(ArrayList<String> listItems) {
        this.listItems = listItems;
    }

    @Override
    public FavoritosAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favs_item_list, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(FavoritosAdapter.ViewHolderItems holder, int position) {
        holder.asignarItems(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        TextView item;

        public ViewHolderItems(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.favs_item);
        }

        public void asignarItems(String s) {
            item.setText(s);
        }
    }
}
