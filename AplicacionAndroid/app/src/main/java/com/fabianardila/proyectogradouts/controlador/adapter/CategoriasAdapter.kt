package com.fabianardila.proyectogradouts.controlador.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabianardila.proyectogradouts.R
import com.fabianardila.proyectogradouts.modelo.Categoria
import kotlinx.android.synthetic.main.item_categoria_card.view.*

class CategoriasAdapter(val clickCategorias: ClickCategorias): RecyclerView.Adapter<CategoriasAdapter.MyViewHolder>(){

    var categoriaList = emptyList<Categoria>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoria = categoriaList[position]
        holder.itemView.title.text = categoria.title
        holder.itemView.lyt_parent.setOnClickListener {
            clickCategorias.onClickCategoriaListener(categoria)
        }
    }

    override fun getItemCount(): Int {
        return categoriaList.size
    }

    interface ClickCategorias {
        fun onClickCategoriaListener(categoria: Categoria)
    }


}