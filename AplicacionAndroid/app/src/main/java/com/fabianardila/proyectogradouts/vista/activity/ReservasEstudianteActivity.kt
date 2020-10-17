package com.fabianardila.proyectogradouts.vista.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Reservas
import com.fabianardila.proyectogradouts.modelo.Status
import com.fabianardila.proyectogradouts.network.Callback
import com.fabianardila.proyectogradouts.network.FirestoreService
import com.fabianardila.proyectogradouts.vista.adapter.ReservasEstudianteAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_listado_usuarios.*
import java.lang.Exception

class ReservasEstudianteActivity : AppCompatActivity(), ReservasEstudianteAdapter.ClickReserva {

    lateinit var firestoreService: FirestoreService
    lateinit var dialogSpots: AlertDialog

    private val reservasAdapter: ReservasEstudianteAdapter = ReservasEstudianteAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas_estudiante)

        dialogSpots = SpotsDialog.Builder().setContext(this).build()
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())

        dialogSpots.show()

        configureRecyclerView()

        obtenerReservaPorUsuario()
    }

    private fun obtenerReservaPorUsuario() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        firestoreService.getReservasByUser(currentUser!!.email!!, object : Callback<List<Reservas>>{
            override fun onSuccess(result: List<Reservas>?) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    reservasAdapter.reservasList = result!!
                    reservasAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                runOnUiThread {
                    dialogSpots.dismiss()
                    Toast.makeText(this@ReservasEstudianteActivity, "No se encontraron reservas", Toast.LENGTH_SHORT).show()
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

    }

    override fun onClickDeleteReserva(reserva: Reservas) {
        MaterialAlertDialogBuilder(this@ReservasEstudianteActivity)
            .setTitle("RESERVA DE LIBROS")
            .setMessage("Vas a eliminar la reserva por el libro ${reserva.libro.titulo}, realizada el dia ${reserva.idDocument}")
            .setPositiveButton("Aceptar") { dialog, which ->
                dialogSpots.show()
                updateStatusLibro(reserva)

            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateStatusLibro(reserva: Reservas) {
        val listStatus = mutableListOf<Status>()
        for (statu in reserva.libro.status!!) {
            if (statu.libroId == reserva.libroStatus.libroId) {
                statu.status = "disponible"
            }
            listStatus.add(statu)
        }
        firestoreService.updateStatusLibro(reserva.libro, listStatus, object : Callback<Void>{
            override fun onSuccess(result: Void?) {
                eliminarReserva(reserva)
            }

            override fun onFailed(exception: Exception) {
                Toast.makeText(this@ReservasEstudianteActivity, "Error al modificar status", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun eliminarReserva(reserva: Reservas) {
        firestoreService.deleteReserva(reserva, object : Callback<Void>{
            override fun onSuccess(result: Void?) {
                showDialogSucess()
            }

            override fun onFailed(exception: Exception) {
                Toast.makeText(this@ReservasEstudianteActivity, "Error al eliminar la reserva", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showDialogSucess() {
        dialogSpots.show()
        MaterialAlertDialogBuilder(this@ReservasEstudianteActivity)
            .setTitle("RESERVA DE LIBROS")
            .setMessage("Reserva eliminada con exito")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, which ->
                startActivity(Intent(this@ReservasEstudianteActivity, MenuEstudiantesActivity::class.java))
                finish()

            }
            .show()
    }
}