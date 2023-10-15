package com.example.forcavendasapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.model.Item;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private ArrayList<Item> listaItems;
    private Context context;

    public ItemListAdapter(ArrayList<Item> listaItems, Context context) {
        this.listaItems = listaItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.activity_item_list,
                parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder holder, int position) {
        holder.tvDescricao.setText(String.valueOf(listaItems.get(position).getDescricao()));
        holder.tvUnMedida.setText(listaItems.get(position).getUnMedida());
        holder.tvValor.setText(String.valueOf(listaItems.get(position).getVlrUnit()));
    }

    @Override
    public int getItemCount() {
        return this.listaItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvDescricao, tvUnMedida, tvValor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvDescricao = itemView.findViewById(R.id.tvDescricao);
            this.tvUnMedida = itemView.findViewById(R.id.tvUnMedida);
            this.tvValor = itemView.findViewById(R.id.tvValor);

        }
    }

}
