package com.fabianardila.proyectogradouts.vista.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User
import com.fabianardila.proyectogradouts.controlador.network.Callback
import com.fabianardila.proyectogradouts.controlador.network.FirestoreService
import com.fabianardila.proyectogradouts.controlador.adapter.UsuariosAdapter
import com.fabianardila.proyectogradouts.controlador.adapter.UsuariosAdapterListener
import com.google.firebase.firestore.FirebaseFirestore
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
            startActivity(Intent(this@ListadoUsuariosActivity, BuscarUsuarioActivity::class.java))
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

    private var selectTipoUsuario: String? = null
    private val opcionesTipoUsuario = arrayOf(
        "Estudiante", "Bibliotecario"
    )
    private var posInit = 0
    private var selectOpc = 0


    override fun onClickCambiarTipo(user: User) {

        if (user.bibliotecario) {
            selectTipoUsuario = opcionesTipoUsuario[1]
            posInit = 1
        } else {
            selectTipoUsuario = opcionesTipoUsuario[0]
            posInit = 0
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Seleciona el tipo de usuario")
        builder.setSingleChoiceItems(opcionesTipoUsuario, posInit,
            DialogInterface.OnClickListener { dialogInterface, i ->
                selectTipoUsuario = opcionesTipoUsuario.get(i)
                selectOpc = i
            })
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (posInit == selectOpc) {
                    dialogInterface.dismiss()
                } else {
                    updateTipoUsuario(user)
                }

            })
        builder.setNegativeButton("CANCEL", null)
        builder.show()
    }

    private fun updateTipoUsuario(user: User) {
        val bibliotecario = user.bibliotecario
        user.bibliotecario = !bibliotecario
        firestoreService.updateTipoUser(user, object : Callback<User>{
            override fun onSuccess(result: User?) {
                cargarUsuarios()
            }

            override fun onFailed(exception: java.lang.Exception) {
                Toast.makeText(applicationContext, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
            }

        })
    }
}