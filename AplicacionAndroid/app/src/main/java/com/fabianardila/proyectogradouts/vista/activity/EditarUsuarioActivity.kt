package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.fabianardila.proyectogradouts.controlador.network.Callback
import com.fabianardila.proyectogradouts.controlador.network.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_editar_usuario.*
import java.lang.Exception

class EditarUsuarioActivity : AppCompatActivity() {

    private var user: User? = null
    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        user = intent.extras!!["user"] as User

        etNombre.setText(user!!.nombre)
        etApellidos.setText(user!!.apellido)
        etProgramaAcademico.setText(user!!.programaAcademico)
        etCorreo.setText(user!!.correo)
        etCelular.setText(user!!.celular)
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

    fun clickActualizarUsuario(view: View) {
        var isCompleteData: Boolean = true

        if (etNombre.text.toString().isEmpty()) {
            isCompleteData = false
            tilNombre.error = "Campo requerido"
        }

        if (etApellidos.text.toString().isEmpty()) {
            isCompleteData = false
            tilNombre.error = "Campo requerido"
        }

        if (etProgramaAcademico.text.toString().isEmpty()) {
            isCompleteData = false
            tilProgramaAcademico.error = "Campo requerido"
        }

        if (isCompleteData){
            user!!.nombre = etNombre.text.toString()
            user!!.apellido = etApellidos.text.toString()
            user!!.programaAcademico = etProgramaAcademico.text.toString()
            user!!.celular = etCelular.text.toString()

            firestoreService.updateUser(user!!, object : Callback<User>{
                override fun onSuccess(result: User?) {
                    finish()
                }

                override fun onFailed(exception: Exception) {

                }

            })
        }
    }
}