package com.fabianardila.proyectogradouts.modelo

import java.io.Serializable

class User : Serializable {
    var id: String = ""
    var nombre: String = ""
    var apellido: String = ""
    var correo: String = ""
    var celular: String = ""
    var programaAcademico: String = ""
    var librosList = null
    var bibliotecario: Boolean = false
}