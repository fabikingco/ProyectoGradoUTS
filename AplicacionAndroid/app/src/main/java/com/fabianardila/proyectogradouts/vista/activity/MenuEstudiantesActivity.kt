package com.fabianardila.proyectogradouts.vista.activity


import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dmax.dialog.SpotsDialog
import java.lang.Exception

class MenuEstudiantesActivity : AppCompatActivity() {

    lateinit var firestoreService: FirestoreService
    lateinit var dialogSpots: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_estudiantes)
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        dialogSpots = SpotsDialog.Builder().setContext(this).build()
    }

    fun clickVisualizarLibros(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, ListadoCategoriasActivity::class.java))
    }

    fun clickMiPerfil(view: View) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val intent = Intent(this@MenuEstudiantesActivity, PerfilUsuarioActivity::class.java)
        dialogSpots.show()
        firestoreService.findUserByEmail(currentUser!!.email.toString(), object: Callback<User>{
            override fun onSuccess(result: User?) {
                intent.putExtra("user", result)
                dialogSpots.dismiss()
                startActivity(intent)
            }

            override fun onFailed(exception: Exception) {
                //TODO("Not yet implemented")
            }
        })

    }

    fun clickCerrarSesion(view: View) {
        MaterialAlertDialogBuilder(this@MenuEstudiantesActivity)
            .setTitle("Desea cerrar sesión?")
            .setPositiveButton(R.string.cerrar_sesion) { dialog, which ->
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                startActivity(Intent(this@MenuEstudiantesActivity, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun clickSobreNosotros(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, AboutActivity::class.java))
    }

    fun clickReservasEstudiante(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, ReservasEstudianteActivity::class.java))
    }


}