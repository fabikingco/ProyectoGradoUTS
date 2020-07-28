package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.SearchLibrosActivity
import com.fabianardila.proyectogradouts.modelo.User
import com.fabianardila.proyectogradouts.vista.adapter.UsuariosAdapter
import com.fabianardila.proyectogradouts.vista.adapter.UsuariosAdapterListener
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import kotlinx.android.synthetic.main.activity_listado_usuarios.*

class ListadoUsuariosActivity : AppCompatActivity(), UsuariosAdapterListener {

    lateinit var firestoreService: FirestoreService

    private val usuariosAdapter: UsuariosAdapter = UsuariosAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_usuarios)

        initToolbar()

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        configureRecyclerView()

        cargarUsuarios()
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Listado de usuarios"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal_no_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            Toast.makeText(applicationContext, "Opcion no disponible", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
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

    override fun onClickRestaurar(user: User) {
        //TODO("Not yet implemented")
        Toast.makeText(applicationContext, "Opcion 1", Toast.LENGTH_SHORT).show()
    }

    override fun onClickCambiarTipo(user: User) {
        //TODO("Not yet implemented")
        Toast.makeText(applicationContext, "Opcion 2", Toast.LENGTH_SHORT).show()
    }
}