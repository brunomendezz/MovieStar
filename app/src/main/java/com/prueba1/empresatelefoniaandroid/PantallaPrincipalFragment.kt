package com.prueba1.empresatelefoniaandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.prueba1.empresatelefoniaandroid.databinding.FragmentPantallaPrincipalBinding

class PantallaPrincipalFragment : Fragment() {
    private lateinit var binding: FragmentPantallaPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPantallaPrincipalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // OPCION AGREGAR CLIENTE
        binding.agregarCliente.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_pantallaPrincipal_to_agregarClientes)
        }

//OPCION BUSCAR COSTO POR ID
        binding.consultarCostoDeUnCliente.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_pantallaPrincipal_to_buscarClientes)
        }
//OPCION CONSULTAR EL COSTO TOTAL
        binding.consultarCostoTotal.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_pantallaPrincipal_to_clientesEncontrados)
        }

        //OPCION SALIR


        binding.botonsalir.setOnClickListener {
            android.os.Process.killProcess(android.os.Process.myPid())
        }

        return root
    }
}
