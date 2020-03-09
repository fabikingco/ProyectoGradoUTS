package com.fabianardila.proyectogradouts;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabianardila.proyectogradouts.modelos.Categorias;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCategoriasGrid extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Categorias> categorias = new ArrayList<>();

    public AdaptadorCategoriasGrid(Context context, List<Categorias> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
