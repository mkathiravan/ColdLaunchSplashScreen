package com.example.kabali.coldlaunchsplashscreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> items = new ArrayList();


    public PaginationAdapter()
    {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return ItemViewHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((ItemViewHolder)viewHolder).bind(items.get(i));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItems(List<String> items)
    {
        this.items.addAll(items);
    }






    private static class ItemViewHolder extends RecyclerView.ViewHolder
    {

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        static ItemViewHolder create(ViewGroup parent)
        {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagination,parent,false));
        }


        void bind(String content)
        {
            ((TextView)itemView).setText(content);
        }
    }
}
