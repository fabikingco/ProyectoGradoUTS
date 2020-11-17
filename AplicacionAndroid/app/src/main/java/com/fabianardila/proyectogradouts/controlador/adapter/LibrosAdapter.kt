package com.fabianardila.proyectogradouts.controlador.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.squareup.picasso.Picasso

class LibrosAdapter (val librosAdapterListener: LibrosAdapterListener):
    RecyclerView.Adapter<LibrosAdapter.ViewHolder>(){

    var librosList: List<Libro> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return librosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libro = librosList[position]
        Picasso.get().load(libro.imageUrl).into(holder.imageLibros)
        holder.tituloLibro.text = libro.titulo
        holder.autorLibro.text = libro.autores
        holder.btnAlquilarLibro.setOnClickListener {
            librosAdapterListener.onClickLibroListener(libro)
        }
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var imageLibros = view.findViewById<ImageView>(R.id.imageLibros)
        var tituloLibro = view.findViewById<TextView>(R.id.tituloLibro)
        var autorLibro = view.findViewById<TextView>(R.id.autorLibro)
        var btnAlquilarLibro = view.findViewById<Button>(R.id.btnAlquilarLibro)
    }
}