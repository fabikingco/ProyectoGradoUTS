package com.fabianardila.proyectogradouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fabianardila.proyectogradouts.modelo.Categorias;
import com.fabianardila.proyectogradouts.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListarCategoriasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categorias);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Toolbar");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal_no_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_sesion){
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<Categorias> items = new ArrayList<>();
        items.add(new Categorias("1", "Generalidades"));
        items.add(new Categorias("1", "Filosofía y psicología"));
        items.add(new Categorias("1", "Religión"));
        items.add(new Categorias("1", "Ciencias sociales"));
        items.add(new Categorias("1", "Matemáticas y ciencias naturales"));
        items.add(new Categorias("1", "Tecnología y ciencias aplicadas"));
        items.add(new Categorias("1", "Artes"));
        items.add(new Categorias("1", "Literatura"));
        items.add(new Categorias("1", "Historia y geografía"));

        //set data and list adapter
        AdaptadorCategoriasGrid mAdapter = new AdaptadorCategoriasGrid(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdaptadorCategoriasGrid.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Categorias categorias, int position) {
                Intent intent = new Intent();
                intent.putExtra("categoria", categorias);
                intent.setClass(ListarCategoriasActivity.this, ListTabsSubCategoriasActivity.class);
                startActivity(intent);
            }
        });

    }
}
