package com.fabianardila.proyectogradouts.vista.adapter

import com.fabianardila.proyectogradouts.modelo.Libro

interface LibrosAdapterListener {
    fun onClickLibroListener(libro: Libro)
}