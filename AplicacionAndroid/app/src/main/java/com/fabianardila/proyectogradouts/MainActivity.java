package com.fabianardila.proyectogradouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Toolbar");
    }

    public void btnEstudiantes(View view) {
        startActivity(new Intent(this, ListarCategoriasActivity.class));
        finish();
    }

    public void btnBibliotecario(View view) {
        startActivity(new Intent(this, LoginBibliotecarioActivity.class));
        finish();
    }
}
