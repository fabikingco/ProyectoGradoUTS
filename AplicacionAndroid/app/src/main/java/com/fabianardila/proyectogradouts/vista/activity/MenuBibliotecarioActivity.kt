package com.fabianardila.proyectogradouts.vista.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fabianardila.proyectogradouts.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MenuBibliotecarioActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_bibliotecario)
    }

    fun clickModificarUsuario(view: View) {
        val intent = Intent(this@MenuBibliotecarioActivity, ListadoUsuariosActivity::class.java)
        startActivity(intent)
    }

    fun clickCerrarSesion(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Desea cerrar sesión?")
        builder.setPositiveButton(R.string.cerrar_sesion,
            DialogInterface.OnClickListener { dialogInterface, i ->
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                startActivity(Intent(this@MenuBibliotecarioActivity, LoginActivity::class.java))
                finish()
            })
        builder.setNegativeButton(getString(R.string.cancelar), null)
        builder.show()
    }
}