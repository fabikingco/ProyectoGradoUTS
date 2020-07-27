package com.fabianardila.proyectogradouts.vista.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_libro.*

class LibroActivity : AppCompatActivity() {

    private var libro: Libro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        libro = intent.extras!!["Libro"] as Libro

        Picasso.get().load(libro!!.imageUrl).into(imageLibro)
        tvTitleLibro.text = libro!!.titulo
        tvAutoresLibro.text = libro!!.autores
        tvEdicionLibro.text = libro!!.edicion
        tvFechaPublicacionLibro.text = libro!!.fechaPublicacion
    }
}