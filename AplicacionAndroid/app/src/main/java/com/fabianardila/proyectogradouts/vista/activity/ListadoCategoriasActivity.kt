package com.fabianardila.proyectogradouts.vista.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.Tools
import com.fabianardila.proyectogradouts.modelo.Categoria
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.fabianardila.proyectogradouts.vista.adapter.CategoriasAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_listado_libros.*

class ListadoCategoriasActivity : AppCompatActivity(), CategoriasAdapter.ClickCategorias {

    private val TAG = "ListadoCategoriasActivity"

    lateinit var firestoreService: FirestoreService

    private val adapter = CategoriasAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_libros)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        initToolbar()

        val recyclerView = recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false

        firestoreService.getCategorias(object : Callback<List<Categoria>> {
            override fun onSuccess(result: List<Categoria>?) {
                runOnUiThread {
                    Toast.makeText(this@ListadoCategoriasActivity, "Cantidad " + result!!.size.toString(), Toast.LENGTH_LONG).show()
                    adapter.categoriaList = result!!
                    adapter.notifyDataSetChanged()
                    if (result.isEmpty()) {
                        imgNoData.visibility = View.VISIBLE
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailed(exception: Exception) {
                Log.d("TAG", "Error al encontrar libros")
                runOnUiThread {
                    imgNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                }
            }

        })

    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Categorias de libros"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal_no_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            startActivity(Intent(this@ListadoCategoriasActivity, BuscarUsuarioActivity::class.java))
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickCategoriaListener(categoria: Categoria) {
        val intent = Intent()
        intent.putExtra("filter", "categoria")
        intent.putExtra("dataFilter", categoria.id)
        intent.setClass(this@ListadoCategoriasActivity, ListadoLibrosActivity::class.java)
        startActivity(intent)
    }
}