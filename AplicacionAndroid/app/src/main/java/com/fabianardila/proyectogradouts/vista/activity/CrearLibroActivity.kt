package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Categoria
import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_crear_libro.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.item_text.view.*

class CrearLibroActivity : AppCompatActivity() {

    var listCategoria = listOf<Categoria>()
    val listTitleCategoria = mutableListOf<String>()
    var selectCategoria = Categoria()

    val listIsbn = mutableListOf<String>()
    val listOtrosTitulos = mutableListOf<String>()
    val adapterIsbn = AdapterIsbn()
    val adapterOtrosTitulos = AdapterOtrosTitulos()

    lateinit var firestoreService: FirestoreService
    lateinit var dialogSpots: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_libro)

        dialogSpots = SpotsDialog.Builder().setContext(this).build()
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        dialogSpots.show()

        initToolbar()

        obtenerCategorias()

        recyclerViewIsbn.adapter = adapterIsbn
        recyclerViewIsbn.layoutManager = LinearLayoutManager(this)
        recyclerViewIsbn.setHasFixedSize(true)
        recyclerViewIsbn.isNestedScrollingEnabled = false


        recyclerViewOtrosTitulos.adapter = adapterOtrosTitulos
        recyclerViewOtrosTitulos.layoutManager = LinearLayoutManager(this)
        recyclerViewOtrosTitulos.setHasFixedSize(true)
        recyclerViewOtrosTitulos.isNestedScrollingEnabled = false
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Crear libro"
    }

    private fun obtenerCategorias() {
        firestoreService.getListCategorias(object : Callback<List<Categoria>> {
            override fun onSuccess(result: List<Categoria>?) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    Toast.makeText(
                        this@CrearLibroActivity,
                        "Cantidad " + result!!.size.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    if (result.isEmpty()) {
                        dialogError()
                    } else {
                        listCategoria = result
                        for (categoria in result) {
                            listTitleCategoria.add(categoria.title)
                        }
                    }
                }

            }

            override fun onFailed(exception: Exception) {
                dialogSpots.dismiss()
            }
        })
    }

    private fun dialogError() {
        MaterialAlertDialogBuilder(this@CrearLibroActivity)
            .setMessage("Error")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun listCategorias(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Selecciona la categoria")
            .setItems(listTitleCategoria.toTypedArray()) { dialog, which ->
                etCategoria.setText(listTitleCategoria[which])
                selectCategoria = listCategoria[which]
            }
            .show()
    }

    fun clickAddIsbn(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Agregar ISBN")
            .setView(R.layout.edit_text_isbn)
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                val input =
                    (dialog as androidx.appcompat.app.AlertDialog).findViewById<TextView>(android.R.id.text1)
                Toast.makeText(this, input!!.text, Toast.LENGTH_LONG).show()
                addTextIsbn(input!!.text.toString())
            }
            .show()
    }

    private fun addTextIsbn(text: String) {
        listIsbn.add(text)
        adapterIsbn.listString = listIsbn
        adapterIsbn.notifyDataSetChanged()
    }

    fun clickAddOtroTitulo(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Agregar otro titulo")
            .setView(R.layout.edit_text_otros_titulos)
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                val input =
                    (dialog as androidx.appcompat.app.AlertDialog).findViewById<TextView>(android.R.id.text1)
                Toast.makeText(this, input!!.text, Toast.LENGTH_LONG).show()
                addTextOtroTitulo(input!!.text.toString())
            }
            .show()
    }

    private fun addTextOtroTitulo(text: String) {
        listOtrosTitulos.add(text)
        adapterOtrosTitulos.listString = listOtrosTitulos
        adapterOtrosTitulos.notifyDataSetChanged()
    }


    class AdapterIsbn : RecyclerView.Adapter<AdapterIsbn.MyViewHolder>(){

        var listString = emptyList<String>()

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_text,
                parent,
                false
            )
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val texto = listString[position]
            holder.itemView.tvText.text = texto
        }

        override fun getItemCount(): Int {
            return listString.size
        }
    }

    class AdapterOtrosTitulos : RecyclerView.Adapter<AdapterOtrosTitulos.MyViewHolder>(){

        var listString = emptyList<String>()

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_text,
                parent,
                false
            )
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val texto = listString[position]
            holder.itemView.tvText.text = texto
        }

        override fun getItemCount(): Int {
            return listString.size
        }
    }

    fun addImage(view: View) {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(
            pickPhoto,
            100
        )
    }

    fun crearLibro(view: View) {
        var isCompleteData: Boolean = true

        if (etTitulo.text.toString().isEmpty()) {
            isCompleteData = false
            tilTitulo.error = "Campo requerido"
        }

        if (etAutores.text.toString().isEmpty()) {
            isCompleteData = false
            tilAutores.error = "Campo requerido"
        }

        if (etFecha.text.toString().isEmpty()) {
            isCompleteData = false
            tilFecha.error = "Campo requerido"
        }

        if (etEdicion.text.toString().isEmpty()) {
            isCompleteData = false
            tilEdicion.error = "Campo requerido"
        }

        if (etCategoria.text.toString().isEmpty()) {
            isCompleteData = false
            tilCategoria.error = "Campo requerido"
        }

        if (isCompleteData) {
            val libro = Libro()
            libro.titulo = etTitulo.text.toString()
            libro.autores = etAutores.text.toString()
            libro.fechaPublicacion = etFecha.text.toString()
            libro.categoria = selectCategoria.id
            libro.edicion = etEdicion.text.toString()
        }
    }
}