package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_buscar_libro.*

class BuscarLibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_libro)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Buscar libro"
    }

    fun clickBorrarSeleccion(view: View) {
        etBusqueda.setText("")
        chip_group.clearCheck()
    }

    fun clickBuscar(view: View) {
        val intent = Intent()
        when {
            chipTitulo.id == chip_group.checkedChipId -> {
                intent.putExtra("filter", "titulo")
                intent.putExtra("dataFilter", etBusqueda.text.toString())
                intent.setClass(this@BuscarLibroActivity, ListadoLibrosActivity::class.java)
                startActivity(intent)
            }
            chipAutores.id == chip_group.checkedChipId -> {
                intent.putExtra("filter", "autores")
                intent.putExtra("dataFilter", etBusqueda.text.toString())
                intent.setClass(this@BuscarLibroActivity, ListadoLibrosActivity::class.java)
                startActivity(intent)
            }
            else -> {
                Snackbar.make(
                    view,
                    "Selecciona un filtro",
                    Snackbar.LENGTH_LONG
                ).setAction("Info", null).show()
            }
        }
    }
}