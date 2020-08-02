package com.fabianardila.proyectogradouts.vista.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.fabianardila.proyectogradouts.vista.adapter.LibrosAdapter
import com.fabianardila.proyectogradouts.vista.adapter.LibrosStatusAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_libro.*
import kotlinx.android.synthetic.main.activity_libro.recyclerView
import kotlinx.android.synthetic.main.activity_listado_libros.*
import kotlinx.android.synthetic.main.activity_login.*

class LibroActivity : AppCompatActivity() {

    private val TAG = "LibroActivity"

    lateinit var firestoreService: FirestoreService

    private var libro: Libro? = null

    private val librosAdapter: LibrosStatusAdapter = LibrosStatusAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        libro = intent.extras!!["Libro"] as Libro

        cargarVistasDelLibros(libro!!)
        configureRecyclerView()
        cargarRecyclerLbrosStatus(libro!!.status!!)
    }

    private fun cargarVistasDelLibros(libro: Libro) {
        Picasso.get().load(libro.imageUrl).into(imageLibro)
        tvTitleLibro.text = libro.titulo
        tvAutoresLibro.text = libro.autores
        tvEdicionLibro.text = libro.edicion
        tvId.text = libro.id
        tvFechaPublicacionLibro.text = libro.fechaPublicacion

    }

    private fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = librosAdapter
    }

    private fun cargarRecyclerLbrosStatus(librosStatusList: List<Map<String, String>>) {
        librosAdapter.librosStatusList = librosStatusList
        librosAdapter.notifyDataSetChanged()
    }

    fun clickFabLibro(view: View) {
        Snackbar.make(
            view,
            "Click Fab",
            Snackbar.LENGTH_LONG
        ).setAction("Info", null).show()
    }
}