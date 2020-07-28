package com.fabianardila.proyectogradouts.vista.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.modelo.User
import com.fabianardila.proyectogradouts.vista.adapter.UsuariosAdapter
import com.fabianardila.proyectogradouts.vista.adapter.UsuariosAdapterListener
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import kotlinx.android.synthetic.main.activity_listado_usuarios.*
import kotlinx.android.synthetic.main.activity_listado_usuarios.recyclerView
import java.lang.Exception

class ListadoUsuariosActivity : AppCompatActivity(), UsuariosAdapterListener {

    lateinit var firestoreService: FirestoreService

    private val usuariosAdapter: UsuariosAdapter = UsuariosAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_usuarios)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        configureRecyclerView()

        cargarUsuarios()


    }

    private fun cargarUsuarios() {
        firestoreService.getUsuarios(object : Callback<List<User>> {
            override fun onSuccess(result: List<User>?) {
                this@ListadoUsuariosActivity.runOnUiThread {
                    usuariosAdapter.usuariosList = result!!
                    usuariosAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = usuariosAdapter
    }

    override fun onClickUsuarioListener(user: User) {
        TODO("Not yet implemented")
    }
}