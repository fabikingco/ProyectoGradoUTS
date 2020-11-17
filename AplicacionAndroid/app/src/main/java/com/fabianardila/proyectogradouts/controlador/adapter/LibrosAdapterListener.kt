package com.fabianardila.proyectogradouts.controlador.adapter

import com.fabianardila.proyectogradouts.modelo.Libro

interface LibrosAdapterListener {
    fun onClickLibroListener(libro: Libro)
}