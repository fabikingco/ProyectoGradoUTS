package com.fabianardila.proyectogradouts.vista.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.User

class UsuariosAdapter (val usuariosAdapterListener: UsuariosAdapterListener):
    RecyclerView.Adapter<UsuariosAdapter.ViewHolder>() {

    var usuariosList: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usuariosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario: User = usuariosList[position]
        holder.tvNombre.text = usuario.nombre + " " + usuario.apellido
        holder.tvCorreo.text = usuario.correo
        if (usuario.bibliotecario) {
            holder.tvTipoUsuario.text = "Bibliotecario"
        } else {
            holder.tvTipoUsuario.text = "Estudiante"
        }
        holder.clickRestaurar.setOnClickListener {
            usuariosAdapterListener.onClickRestaurar(usuario)
        }
        holder.clickCambiarTipo.setOnClickListener {
            usuariosAdapterListener.onClickCambiarTipo(usuario)
        }
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        var tvCorreo = view.findViewById<TextView>(R.id.tvCorreo)
        var tvTipoUsuario = view.findViewById<TextView>(R.id.tvTipoUsuario)
        var clickRestaurar = view.findViewById<RelativeLayout>(R.id.clickRestaurar)
        var clickCambiarTipo = view.findViewById<RelativeLayout>(R.id.clickCambiarTipo)
    }
}