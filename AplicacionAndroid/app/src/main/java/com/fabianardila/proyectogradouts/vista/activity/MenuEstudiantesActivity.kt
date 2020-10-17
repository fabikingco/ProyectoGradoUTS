package com.fabianardila.proyectogradouts.vista.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import java.lang.Exception

class MenuEstudiantesActivity : AppCompatActivity() {

    private var user: User? = null

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_estudiantes)
        user = intent.extras!!["user"] as User
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
    }

    fun clickVisualizarLibros(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, ListadoCategoriasActivity::class.java))
    }

    fun clickMiPerfil(view: View) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val intent = Intent(this@MenuEstudiantesActivity, PerfilUsuarioActivity::class.java)
        if (user != null) {
            intent.putExtra("user", user)
            startActivity(intent)
        } else {
            firestoreService.findUserByEmail(currentUser!!.email.toString(), object: Callback<User>{
                override fun onSuccess(result: User?) {
                    intent.putExtra("user", result)
                    startActivity(intent)
                }

                override fun onFailed(exception: Exception) {
                    //TODO("Not yet implemented")
                }
            })
        }

    }

    fun clickCerrarSesion(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Desea cerrar sesión?")
        builder.setPositiveButton(R.string.cerrar_sesion,
            DialogInterface.OnClickListener { dialogInterface, i ->
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                startActivity(Intent(this@MenuEstudiantesActivity, LoginActivity::class.java))
                finish()
            })
        builder.setNegativeButton(getString(R.string.cancelar), null)
        builder.show()
    }

    fun clickSobreNosotros(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, AboutActivity::class.java))
    }

    fun clickReservasEstudiante(view: View) {
        startActivity(Intent(this@MenuEstudiantesActivity, ReservasEstudianteActivity::class.java))
    }


}