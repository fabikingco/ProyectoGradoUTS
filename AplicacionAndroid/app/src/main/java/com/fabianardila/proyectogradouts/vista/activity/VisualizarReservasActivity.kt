package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Reservas
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.fabianardila.proyectogradouts.vista.adapter.ReservasEstudianteAdapter
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_visualizar_reservas.*
import java.lang.Exception

class VisualizarReservasActivity : AppCompatActivity(), ReservasEstudianteAdapter.ClickReserva {

    lateinit var firestoreService: FirestoreService
    lateinit var dialogSpots: AlertDialog

    private val reservasAdapter: ReservasEstudianteAdapter = ReservasEstudianteAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_reservas)

        dialogSpots = SpotsDialog.Builder().setContext(this).build()
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        dialogSpots.show()

        configureRecyclerView()

        initToolbar()

        if (intent.extras != null) {
            val email = intent.extras!!["email"] as String
            if (email.isEmpty()) {
                obtenerTodasLasReservaciones()
            } else {
                obtenerReservaPorUsuario(email)
            }
        } else {
            obtenerTodasLasReservaciones()
        }
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Reservas"
    }

    private fun obtenerTodasLasReservaciones() {
        firestoreService.getReservas(object : Callback<List<Reservas>> {
            override fun onSuccess(result: List<Reservas>?) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    if (result!!.isEmpty()) {
                        Log.d("TAG", "No llegaron reservas")
                        tvNoData.visibility = View.VISIBLE
                        imgNoData.visibility = View.VISIBLE
                    } else {
                        reservasAdapter.reservasList = result
                        reservasAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailed(exception: Exception) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    Toast.makeText(
                        this@VisualizarReservasActivity,
                        "No se encontraron reservas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun obtenerReservaPorUsuario(email: String) {
        firestoreService.getReservasByUser(email, object :
            Callback<List<Reservas>> {
            override fun onSuccess(result: List<Reservas>?) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    if (result!!.isEmpty()) {
                        Log.d("TAG", "No llegaron reservas")
                        tvNoData.visibility = View.VISIBLE
                        imgNoData.visibility = View.VISIBLE
                    } else {
                        reservasAdapter.reservasList = result
                        reservasAdapter.notifyDataSetChanged()
                    }

                }
            }

            override fun onFailed(exception: Exception) {
                runOnUiThread {
                    tvNoData.visibility = View.VISIBLE
                    imgNoData.visibility = View.VISIBLE
                    dialogSpots.dismiss()
                    Toast.makeText(
                        this@VisualizarReservasActivity,
                        "No se encontraron reservas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = reservasAdapter
    }

    override fun onClickReservaListener(reserva: Reservas) {
        TODO("Not yet implemented")
    }

    override fun onClickDeleteReserva(reserva: Reservas) {
        TODO("Not yet implemented")
    }
}