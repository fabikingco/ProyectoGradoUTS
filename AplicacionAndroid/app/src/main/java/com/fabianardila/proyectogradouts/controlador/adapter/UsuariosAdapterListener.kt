package com.fabianardila.proyectogradouts.controlador.adapter

import com.fabianardila.proyectogradouts.modelo.User


interface UsuariosAdapterListener {
    fun onClickRestaurar(user: User)

    fun onClickCambiarTipo(user: User)
}