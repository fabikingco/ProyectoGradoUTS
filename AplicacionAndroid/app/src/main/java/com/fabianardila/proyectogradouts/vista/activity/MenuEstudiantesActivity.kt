package com.fabianardila.proyectogradouts.vista.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.fabianardila.proyectogradouts.ListarCategoriasActivity
import com.fabianardila.proyectogradouts.R
import com.google.firebase.auth.FirebaseAuth

class MenuEstudiantesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_estudiantes)
    }

    fun clickCerrarSesion(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Discard draft ?")
        builder.setPositiveButton(R.string.cerrar_sesion,
            DialogInterface.OnClickListener { dialogInterface, i ->
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                startActivity(Intent(this@MenuEstudiantesActivity, LoginActivity::class.java))
            })
        builder.setNegativeButton("Salir", null)
        builder.show()
    }

    fun clickVisualizarLibros(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, ListarCategoriasActivity::class.java))
    }
}