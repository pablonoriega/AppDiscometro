package com.discometro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VueltaSeguraItemAdapter extends RecyclerView.Adapter<VueltaSeguraItemAdapter.ViewHolderItems> {

    ArrayList<String> listItems;

    public VueltaSeguraItemAdapter(ArrayList<String> listItems) {
        this.listItems = listItems;
    }

    @Override
    public VueltaSeguraItemAdapter.ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vuelta_segura_item_list, null, false);
        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(VueltaSeguraItemAdapter.ViewHolderItems holder, int position) {
        holder.asignarItems(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.idVueltaSeguraItem);
        }

        public void asignarItems(String s) {
            textView.setText(s);
        }
    }
}
