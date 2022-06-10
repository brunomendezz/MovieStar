package com.prueba1.empresatelefoniaandroid

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Client(
    val codigo_cliente: Int = 0,
    val nombre_cliente: String = "",
    val apellido_cliente: String = "",
    val fecha_de_alta_cliente: String = LocalDate.now().toString()
) {


    // SI EL CLIENTE FUE DADO DE HACE MENOS DE 6 MESES ES NUEVO(TRUE) SI NO NO LO ES (FALSE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarFechaDeAltaDelCliente(nroDeClienteABuscar: Int): Boolean {

        val listaDeClientes = ClientRepository.get()
        val fechaActual = LocalDate.now()

        listaDeClientes.forEach() {
            if (it.codigo_cliente == nroDeClienteABuscar) {
                var fecha = LocalDate.parse(it.fecha_de_alta_cliente)
                if (fecha in fechaActual.minusMonths(6)..fechaActual) {
                    return true
                }
            }
        }
        return false
    }
}
