package com.fabianardila.proyectogradouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fabianardila.proyectogradouts.modelo.User;
import com.fabianardila.proyectogradouts.vista.activity.LibroActivity;
import com.fabianardila.proyectogradouts.vista.activity.ListadoLibrosActivity;
import com.fabianardila.proyectogradouts.vista.activity.MenuBibliotecarioActivity;
import com.fabianardila.proyectogradouts.vista.activity.MenuEstudiantesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        if (user != null && user.getBibliotecario()) {
            startActivity(new Intent(this, MenuBibliotecarioActivity.class));
        } else {
            startActivity(new Intent(this, MenuEstudiantesActivity.class));
        }
        finish();
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
