package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.lang.Exception

class ListadoLibrosActivity : AppCompatActivity(), LibrosAdapterListener {

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
                addRealtimeDatabaseListener(result!!)
                this@ListadoLibrosActivity.runOnUiThread {
                    librosAdapter.librosList = result!!
                    librosAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun cargarLibrosPorCategoria(categoria: String) {
        firestoreService.getLibrosByCategoria(categoria, object : Callback<List<Libro>> {
            override fun onSuccess(result: List<Libro>?) {
                addRealtimeDatabaseListener(result!!)
                this@ListadoLibrosActivity.runOnUiThread {
                    librosAdapter.librosList = result!!
                    librosAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                //TODO("Not yet implemented")
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
        //TODO("Not yet implemented")
        val intent = Intent(this@ListadoLibrosActivity, LibroActivity::class.java)
        intent.putExtra("Libro", libro)
        startActivity(intent)
    }
}