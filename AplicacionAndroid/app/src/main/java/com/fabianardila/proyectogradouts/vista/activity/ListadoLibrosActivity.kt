package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Categorias
import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.vista.adapter.LibrosAdapter
import com.fabianardila.proyectogradouts.vista.adapter.LibrosAdapterListener
import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.network.Callback
import com.platzi.android.firestore.network.FirestoreService
import com.platzi.android.firestore.network.RealtimeDataListener
import kotlinx.android.synthetic.main.activity_listado_libros.*

class ListadoLibrosActivity : AppCompatActivity(), LibrosAdapterListener {

    private val TAG = "ListadoLibrosActivity"

    lateinit var firestoreService: FirestoreService

    private val librosAdapter: LibrosAdapter = LibrosAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_libros)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        configureRecyclerView()

        val bundle = intent.extras
        if (bundle != null) {
            val categoria = bundle["categoria"] as Categorias
            if (categoria.id != null) {
                if (categoria.id == "1000" || categoria.id == "") {
                    cargarLibros()
                } else {
                    cargarLibrosPorCategoria(categoria.id)
                }
            }
        } else {
            cargarLibros()
        }
    }

    private fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = librosAdapter
    }

    private fun cargarLibros() {
        firestoreService.getLibros(object : Callback<List<Libro>> {
            override fun onSuccess(result: List<Libro>?) {
                Log.d(TAG, "Consulta de libros exitosa")
                addRealtimeDatabaseListener(result!!)
                this@ListadoLibrosActivity.runOnUiThread {
                    librosAdapter.librosList = result
                    librosAdapter.notifyDataSetChanged()
                    if (result.isEmpty()) {
                        imgNoData.visibility = View.VISIBLE
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailed(exception: Exception) {
                Log.d(TAG, "Error al encontrar libros")
                this@ListadoLibrosActivity.runOnUiThread {
                    imgNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun cargarLibrosPorCategoria(categoria: String) {
        firestoreService.getLibrosByCategoria(categoria, object : Callback<List<Libro>> {
            override fun onSuccess(result: List<Libro>?) {
                Log.d(TAG, "Consulta de libros exitosa por categorias")
                addRealtimeDatabaseListener(result!!)
                this@ListadoLibrosActivity.runOnUiThread {
                    librosAdapter.librosList = result
                    librosAdapter.notifyDataSetChanged()
                    if (result.isEmpty()) {
                        imgNoData.visibility = View.VISIBLE
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailed(exception: Exception) {
                Log.d(TAG, "Error al encontrar libros por categorias")
                this@ListadoLibrosActivity.runOnUiThread {
                    imgNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun addRealtimeDatabaseListener(libroList: List<Libro>) {
        firestoreService.listenForUpdates(libroList, object : RealtimeDataListener<Libro>{
            override fun onDataChange(updatedData: Libro) {
                var pos = 0
                for (libro in librosAdapter.librosList) {
                    if (libro.id.equals(updatedData.id)) {
                        //libro.available = updatedData.available
                        librosAdapter.notifyItemChanged(pos)
                    }
                    pos ++
                }
            }

            override fun onError(exception: Exception) {
                //TODO("Not yet implemented")
            }

        })
    }

    override fun onClickLibroListener(libro: Libro) {
        val intent = Intent(this@ListadoLibrosActivity, LibroActivity::class.java)
        intent.putExtra("Libro", libro)
        startActivity(intent)
    }
}