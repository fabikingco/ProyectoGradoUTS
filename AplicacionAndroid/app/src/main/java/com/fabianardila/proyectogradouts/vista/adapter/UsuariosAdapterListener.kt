package com.fabianardila.proyectogradouts.vista.adapter

import com.fabianardila.proyectogradouts.modelo.User


interface UsuariosAdapterListener {
    fun onClickRestaurar(user: User)

    fun onClickCambiarTipo(user: User)
}