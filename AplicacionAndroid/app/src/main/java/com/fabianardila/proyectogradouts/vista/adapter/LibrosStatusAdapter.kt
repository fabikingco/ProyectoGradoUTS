package com.fabianardila.proyectogradouts.vista.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Libro
import com.google.common.base.Ascii.toLowerCase
import com.squareup.picasso.Picasso

class LibrosStatusAdapter (): RecyclerView.Adapter<LibrosStatusAdapter.ViewHolder>(){

    var librosStatusList: List<Map<String, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro_status, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return librosStatusList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libroStatus = librosStatusList[position]
        val status = libroStatus["status"]
        when {
            toLowerCase(status!!) == "disponible" -> {
                holder.colorBackIconStatus.setColorFilter(Color.rgb(193,8,207))
            }
            toLowerCase(status) == "prestado" -> {
                holder.colorBackIconStatus.setColorFilter(Color.rgb(200,8,207))
            }
            toLowerCase(status) == "reservado" -> {
                holder.colorBackIconStatus.setColorFilter(Color.rgb(155,8,222))
            }
        }
        holder.tvStatus.text = status
        holder.tvFechaRegresoStatus.text = libroStatus["fechaRegreso"]
        holder.codigoLibroStatus.text = "Codiigo: " + libroStatus["libroId"]

    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        //(Se instancian los elementos del item)
        var colorBackIconStatus = view.findViewById<ImageView>(R.id.colorBackIconStatus)
        var iconStatus = view.findViewById<ImageView>(R.id.iconStatus)
        var codigoLibroStatus = view.findViewById<TextView>(R.id.codigoLibroStatus)
        var tvStatus = view.findViewById<TextView>(R.id.tvStatus)
        var tvFechaRegresoStatus = view.findViewById<TextView>(R.id.tvFechaRegresoStatus)


    }
}