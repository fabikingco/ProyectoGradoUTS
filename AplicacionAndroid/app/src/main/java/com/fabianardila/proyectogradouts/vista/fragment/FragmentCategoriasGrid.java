package com.fabianardila.proyectogradouts.vista.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fabianardila.proyectogradouts.R;
import com.fabianardila.proyectogradouts.Tools;
import com.fabianardila.proyectogradouts.widget.SpacingItemDecoration;

public class FragmentCategoriasGrid extends Fragment {

    public FragmentCategoriasGrid() {
    }

    public static FragmentCategoriasGrid newInstance() {
        return new FragmentCategoriasGrid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_category_grid, container, false);


        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 8), true));
        recyclerView.setHasFixedSize(true);


        return root;
    }
}