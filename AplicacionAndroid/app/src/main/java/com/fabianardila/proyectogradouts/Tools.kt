package com.fabianardila.proyectogradouts

import android.util.Patterns

object Tools {

    fun checkEmailAdress(email: String): Boolean {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}