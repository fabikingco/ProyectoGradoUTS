package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.Tools
import kotlinx.android.synthetic.main.activity_buscar_reservas.*

class BuscarReservasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_reservas)

        initToolbar()
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Buscar reservas"
    }

    fun clickBuscar(view: View) {
        val email = etBusqueda.text.toString()
        if (Tools.checkEmailAdress(email)) {
            if (!email.isEmpty()) {
                val intent = Intent(this, VisualizarReservasActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            } else {
                Toast.makeText(this@BuscarReservasActivity, "Escribe un correo", Toast.LENGTH_SHORT).show()
            }
        } else {
            tilBusqueda.error = "Correo no valido"
        }
    }

    fun clickVisualizarTodas(view: View) {
        val intent = Intent(this, VisualizarReservasActivity::class.java)
        startActivity(intent)
    }
}