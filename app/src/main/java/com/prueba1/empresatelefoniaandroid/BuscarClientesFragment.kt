package com.prueba1.empresatelefoniaandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba1.empresatelefoniaandroid.databinding.FragmentBuscarClientesBinding

class BuscarClientesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarClientesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuscarClientesBinding.inflate(inflater, container, false)

        initRecyclerView()

        val nroDeClienteABuscar = binding.idabuscar.text

        val chequeoDeQueSeaNumero = { nroClienteABuscar: String ->
            nroClienteABuscar.forEach {
                if (!it.isDigit())

                    throw java.lang.RuntimeException("INGRESE ID VALIDO")
            }
        }


        //BOTON CANCELAR
        binding.botoncancelar2.setOnClickListener {
            findNavController(binding.root)
                .navigate(R.id.action_buscarClientes_to_pantallaPrincipal)

        }

        //BOTON BUSCAR
        binding.botonbuscar2.setOnClickListener {
            try {
                val listaDeClientes = ClientManager().obtenerListaDeClientes()
                chequeoDeQueSeaNumero(nroDeClienteABuscar.toString())
                if (nroDeClienteABuscar.toString()
                        .toInt() !in 0..listaDeClientes.size || nroDeClienteABuscar.equals(
                        null
                    )
                ) {
                    Toast.makeText(
                        requireActivity(),
                        "ESE ID DE CLIENTE NO EXISTE",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    findNavController()?.navigate(
                        BuscarClientesFragmentDirections.actionBuscarClientesToClientesEncontrados(
                            nroDeClienteABuscar.toString().toInt()
                        )
                    )


                }


            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "INGRESE UN ID VALIDO", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }


    private fun initRecyclerView() {
        val recyclerView = binding.rvListaDeClientes
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = Adapter(ClientManager().obtenerListaDeClientes())
    }
}


