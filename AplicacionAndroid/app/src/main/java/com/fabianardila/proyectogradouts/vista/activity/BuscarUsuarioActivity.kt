package com.fabianardila.proyectogradouts.vista.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_buscar_usuario.*

class BuscarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_usuario)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Buscar usuario"
    }

    fun clickBorrarSeleccion(view: View) {
        etBusqueda.setText("")
        chip_group.clearCheck()
    }
    fun clickBuscar(view: View) {
        val chipChecked = chip_group.checkedChipId
        Snackbar.make(
            view,
            "Click Fab $chipChecked",
            Snackbar.LENGTH_LONG
        ).setAction("Info", null).show()
    }
}