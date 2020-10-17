package com.fabianardila.proyectogradouts.modelo

import com.google.firebase.Timestamp
import java.io.Serializable

class Reservas : Serializable {
    var idDocument: String = ""
    var fechaReserva: Timestamp = Timestamp(0L, 0)
    var fechaFinReserva: Timestamp = Timestamp(0L, 0)
    var fechaPrestamo: Timestamp = Timestamp(0L, 0)
    var fechaFinPrestamo: Timestamp = Timestamp(0L, 0)
    var libroStatus: Status = Status()
    var mailUser: String = ""
    var libro: Libro = Libro()
}