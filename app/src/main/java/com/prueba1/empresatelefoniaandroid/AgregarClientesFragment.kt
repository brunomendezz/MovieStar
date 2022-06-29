package com.prueba1.empresatelefoniaandroid;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.prueba1.empresatelefoniaandroid.databinding.FragmentAgregarClientesBinding

class AgregarClientesFragment : Fragment() {
    private lateinit var binding: FragmentAgregarClientesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgregarClientesBinding.inflate(inflater, container, false)

        binding.botoncancelar1.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_agregarClientes_to_pantallaPrincipal)
        }

        binding.botonregistrar1.setOnClickListener {

            val chequeoDeNoNumeros = { nombreOApellido: String ->
                nombreOApellido.forEach {
                    if (it.isDigit())
                        throw java.lang.RuntimeException("NO INGRESAR NUMEROS ,")
                }
            }

            try {
                val nombre = binding.nombre.text.toString()
                chequeoDeNoNumeros(nombre)
                //PARA QUE NO INGRESEN "ASD" POR EJEMPLO
                check(nombre.length >= 2)

                val apellido = binding.apellido.text.toString()
                chequeoDeNoNumeros(apellido)
                check(apellido.length >= 2)

                if (ClientManager().darDeAltaCliente(nombre, apellido)) {
                    Toast.makeText(
                        requireActivity(),
                        "El cliente se ha agregado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(binding.root).navigate(R.id.action_agregarClientes_to_pantallaPrincipal)
                } else {
                    Toast.makeText(requireActivity(),
                        "No se ha podido registrar el cliente, intente de nuevo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "INGRESE NOMBRE O APELLIDO VALIDO", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }
}
