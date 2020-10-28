package com.fabianardila.proyectogradouts

import android.util.Patterns

object Tools {

    fun checkEmailAdress(email: String): Boolean {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun padLeft(s: String, len: Int, c: Char) : String {
        var data = s
        data = data.trim { it <= ' ' }
        if (data.length > len) {
            return ""
        }
        val d = StringBuilder(len)
        var fill = len - data.length
        while (fill-- > 0) {
            d.append(c)
        }
        d.append(data)
        return d.toString()
    }
}