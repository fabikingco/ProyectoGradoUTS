package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.fabianardila.proyectogradouts.controlador.network.Callback
import com.fabianardila.proyectogradouts.controlador.network.FirestoreService
import com.fabianardila.proyectogradouts.controlador.network.USER_COLECTION_NAME
import kotlinx.android.synthetic.main.activity_registro.*
import java.lang.Exception

class RegistroActivity : AppCompatActivity() {

    private val TAG = "RegistroActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        initToolbar()
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Registro"
    }

    private fun showErrorMessage(view: View) {
        Snackbar.make(view, "Error al conectrar",
            Snackbar.LENGTH_LONG
        ).setAction("Info", null).show()
    }

    fun registrarUsuario(view: View) {
        var isCompleteData: Boolean = true

        if (etNombre.text.toString().isEmpty()) {
            isCompleteData = false
            tilNombre.error = "Campo requerido"
        }

        if (etApellidos.text.toString().isEmpty()) {
            isCompleteData = false
            tilApellidos.error = "Campo requerido"
        }

        if (etCedula.text.toString().isEmpty()) {
            isCompleteData = false
            tilCedula.error = "Campo requerido"
        }

        if (etProgramaAcademico.text.toString().isEmpty()) {
            isCompleteData = false
            tilProgramaAcademico.error = "Campo requerido"
        }

        if (etCorreo.text.toString().isEmpty()) {
            isCompleteData = false
            tilCorreo.error = "Campo requerido"
        }

        if (etPassword.text.toString().isEmpty()) {
            isCompleteData = false
            tilPassword.error = "Campo requerido"
        }

        if (isCompleteData){
            //crear usuario con email y password
            val email: String = etCorreo.text.toString() + "@uts.edu.co"
            auth.createUserWithEmailAndPassword(email, etPassword.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        guardarUsuario(view)
                       /* val user = auth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Email sent.")
                                    guardarUsuario(view)
                                }
                            }*/

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }

    private fun guardarUsuario(view: View) {
        val user = User()
        user.id = etCedula.text.toString()
        user.nombre = etNombre.text.toString()
        user.apellido = etApellidos.text.toString()
        user.programaAcademico = etProgramaAcademico.text.toString()
        user.correo = etCorreo.text.toString() + "@uts.edu.co"
        user.celular = etCelular.text.toString()

        firestoreService.setDocument(user, USER_COLECTION_NAME, user.id, object : Callback<Void> {
            override fun onSuccess(result: Void?) {
                Log.d(TAG, "Documento de usuario creado exitosamente")
                val intent = Intent(this@RegistroActivity, LoginActivity::class.java)
                startActivity(intent)
                auth.signOut()
                finish()
            }

            override fun onFailed(exception: Exception) {
                Log.d(TAG, "Error al crear documento de usuario")
                showErrorMessage(view)
            }

        })

    }

    fun listaProgramasAcademicos(view: View) {
        val arrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.ProgramasAcademicos,
            android.R.layout.simple_list_item_1
        )

        val builderSingle = AlertDialog.Builder(this)
        builderSingle.setTitle("Seleccione un departamento")
        builderSingle.setNegativeButton(
            "Cancelar"
        ) { dialog, which -> dialog.dismiss() }
        builderSingle.setAdapter(
            arrayAdapter
        ) { dialog, which ->
            etProgramaAcademico.setText(arrayAdapter.getItem(which).toString())
            dialog.dismiss()
        }
        builderSingle.show()

    }
    fun scanCedula(view: View) {
        Snackbar.make(view, "Disponible proximamente",
            Snackbar.LENGTH_LONG
        ).setAction("Info", null).show()
        //TODO("Falta por implementar lectura de cedula")
    }
}