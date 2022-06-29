package com.prueba1.empresatelefoniaandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.prueba1.empresatelefoniaandroid.databinding.FragmentClientesEncontradosBinding

class ClientesEncontradosFragment : Fragment() {
    private lateinit var binding: FragmentClientesEncontradosBinding
    val args: ClientesEncontradosFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientesEncontradosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (args.idABuscar == 1000000000) {
            binding.costotaldelcliente.text = "EL COSTO TOTAL DE LOS CLIENTE ES:$ ${
                CallsManager().costoTotalDeLosClientes()
            }"
        } else {
            binding.costotaldelcliente.text = "EL COSTO TOTAL DEL CLIENTE ES:$ ${
                CallsManager().costoDeLlamada(args.idABuscar)
            }"
        }

        binding.botonVolver.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_clientesEncontrados_to_pantallaPrincipal)
        }

        return root
    }
}