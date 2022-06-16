package com.prueba1.empresatelefoniaandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prueba1.empresatelefoniaandroid.databinding.ItemlistBinding


class Adapter(private val clients: List<Client>):RecyclerView.Adapter<Adapter.ClientHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClientHolder(layoutInflater.inflate(R.layout.itemlist,parent,false))
    }
    override fun getItemCount(): Int = clients.size

    override fun onBindViewHolder(holder: ClientHolder, position: Int) {
       holder.render(clients[position])
    }




    class ClientHolder(val view : View): RecyclerView.ViewHolder(view){

        val binding = ItemlistBinding.bind(view)
        fun render(client: Client){


           binding.nombrecompleto.text = "${client.nombre} ${client.apellido}"
            binding.fechadealta.text = client.fecha_de_alta
            binding.codigodecliente.text = client.codigo_cliente.toString()


        }

    }

}