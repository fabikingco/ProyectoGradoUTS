package com.fabianardila.proyectogradouts.vista.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Categoria
import com.fabianardila.proyectogradouts.modelo.Reservas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_reservas.view.*
import java.text.SimpleDateFormat

class ReservasEstudianteAdapter(val clickReserva: ClickReserva): RecyclerView.Adapter<ReservasEstudianteAdapter.MyViewHolder>(){

    var reservasList = emptyList<Reservas>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    interface ClickReserva {
        fun onClickReservaListener(reserva: Reservas)
        fun onClickDeleteReserva(reserva: Reservas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservas, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reserva = reservasList[position]
        holder.itemView.tvStatus.text = reserva.libroStatus.status
        if (reserva.libroStatus.status == "reservado") {
            holder.itemView.tvFechaPrestamo.visibility = View.GONE
            holder.itemView.tvFechaFinPrestamo.visibility = View.GONE
            holder.itemView.colorBackIconStatus.setColorFilter(Color.rgb(255,140,0))
        } else if (reserva.libroStatus.status == "prestado") {
            holder.itemView.tvFechaReserva.visibility = View.GONE
            holder.itemView.tvFechaFinReserva.visibility = View.GONE
            holder.itemView.colorBackIconStatus.setColorFilter(Color.rgb(255,0,0))
        }
        holder.itemView.tituloLibro.text = reserva.libro.titulo
        Picasso.get().load(reserva.libro.imageUrl).into(holder.itemView.imageLibro)
        val pattern = "dd/MMM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        holder.itemView.tvFechaReserva.text = simpleDateFormat.format(reserva.fechaReserva.toDate())
        holder.itemView.tvFechaFinReserva.text = simpleDateFormat.format(reserva.fechaFinReserva.toDate())
        holder.itemView.tvFechaPrestamo.text = simpleDateFormat.format(reserva.fechaPrestamo.toDate())
        holder.itemView.tvFechaFinPrestamo.text = simpleDateFormat.format(reserva.fechaFinPrestamo.toDate())

        holder.itemView.imageDelete.setOnClickListener {
            clickReserva.onClickDeleteReserva(reserva)
        }

    }

    override fun getItemCount(): Int {
        return reservasList.size
    }
}