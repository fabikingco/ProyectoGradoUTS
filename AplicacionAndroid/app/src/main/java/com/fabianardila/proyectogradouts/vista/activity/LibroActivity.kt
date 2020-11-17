package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.modelo.Reservas
import com.fabianardila.proyectogradouts.modelo.Status
import com.fabianardila.proyectogradouts.controlador.network.Callback
import com.fabianardila.proyectogradouts.controlador.network.FirestoreService
import com.fabianardila.proyectogradouts.controlador.adapter.LibrosStatusAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_libro.*
import kotlinx.android.synthetic.main.activity_libro.recyclerView
import java.lang.Exception
import java.util.*

class LibroActivity : AppCompatActivity() {

    private val TAG = "LibroActivity"

    lateinit var firestoreService: FirestoreService

    private var libro: Libro? = null

    private val librosAdapter: LibrosStatusAdapter = LibrosStatusAdapter()

    lateinit var dialogSpots: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        dialogSpots = SpotsDialog.Builder().setContext(this).build()

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

    private fun cargarRecyclerLbrosStatus(librosStatusList: List<Status>) {
        librosAdapter.librosStatusList = librosStatusList
        librosAdapter.notifyDataSetChanged()
    }

    fun clickFabLibro(view: View) {
        var position = 0
        for (libroStatus: Status in libro!!.status!!){
            if (libroStatus.status == "disponible") {
                realizarReserva(libroStatus, position)
                return
            }
            position ++
        }
        mostrarError()
    }

    private fun mostrarError() {
        Toast.makeText(this@LibroActivity, "No disponibles", Toast.LENGTH_LONG).show()
    }

    private fun realizarReserva(status: Status, position: Int) {
        val listDeStatus = libro!!.status!!.toMutableList()
        status.status = "reservado"
        listDeStatus[position] = status
        //Toast.makeText(this@LibroActivity, "El " + listDeStatus[position].libroId + " sera" + listDeStatus[position].status, Toast.LENGTH_LONG).show()

        MaterialAlertDialogBuilder(this@LibroActivity)
            .setTitle("RESERVA DE LIBROS")
            .setMessage("Vas a reservar el libro ${libro!!.titulo} con el codigo numero ${status.libroId}")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, which ->
                dialogSpots.show()
                crearReserva(listDeStatus, status)
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun crearReserva(listDeStatus: MutableList<Status>, status: Status) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val reservas: Reservas = Reservas()
        reservas.mailUser = currentUser!!.email!!
        reservas.fechaReserva = Timestamp.now()
        val dateReserva = reservas.fechaReserva.toDate()
        val calendar = Calendar.getInstance()
        calendar.time = dateReserva
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        val dateFinReserva = calendar.time
        reservas.fechaFinReserva = Timestamp(dateFinReserva)
        reservas.libro = libro!!
        reservas.libroStatus = status


        firestoreService.setReserva(reservas, object : Callback<Void> {
            override fun onSuccess(result: Void?) {
                updateStatusLibro(listDeStatus)
            }

            override fun onFailed(exception: Exception) {
                mostrarError()
            }

        })

    }

    private fun updateStatusLibro(listDeStatus: MutableList<Status>) {
        firestoreService.updateStatusLibro(libro!!, listDeStatus, object : Callback<Void> {
            override fun onSuccess(result: Void?) {
                showDialogSuccess()
            }

            override fun onFailed(exception: Exception) {
                runOnUiThread {
                    dialogSpots.show()
                    Toast.makeText(this@LibroActivity, "Fallo", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showDialogSuccess() {
        dialogSpots.dismiss()
        MaterialAlertDialogBuilder(this@LibroActivity)
            .setTitle("RESERVA DE LIBROS")
            .setMessage("Has reservado correctamente el libro. Acercate a la biblioteca para retirarlo.")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, which ->
                dialog.dismiss()
                startActivity(Intent(this@LibroActivity, MenuEstudiantesActivity::class.java))
            }
            .show()
    }


}