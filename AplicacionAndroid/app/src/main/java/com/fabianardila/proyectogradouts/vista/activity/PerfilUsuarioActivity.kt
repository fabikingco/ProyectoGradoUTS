package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import kotlinx.android.synthetic.main.activity_perfil_usuario.*

class PerfilUsuarioActivity : AppCompatActivity() {

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)
        initToolbar()
        user = intent.extras!!["user"] as User

        tvUserNombreApellido.text = user!!.nombre + " " + user!!.apellido
        tvUserCorreo.text = user!!.correo
        tvUserProgramaAcademico.text = user!!.programaAcademico
        tvUserDocumento.text = user!!.id
        tvUserTelefono.text = user!!.celular
        if (user!!.bibliotecario) {
            tvUserTipoUsuario.text = getString(R.string.bibliotecario)
        } else {
            tvUserTipoUsuario.text = getString(R.string.estudiante)
        }

    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Mi perfil"
    }

    fun clickEditarUsuario(view: View) {
        val intent = Intent(this, EditarUsuarioActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}