package com.prueba1.empresatelefoniaandroid

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate


class ClientManager {
    private val db = FirebaseFirestore.getInstance()

    fun darDeAltaCliente(nombre: String, apellido: String): Boolean {

        val codigoDeCliente = ClientRepository.get().size + 1
        val fechaDeAlta = LocalDate.now().toString()
        val nuevoCliente = Client(apellido,codigoDeCliente , fechaDeAlta,nombre)

        val chequeo = ClientRepository.get().add(nuevoCliente)
        if (chequeo) {
            db.collection("Clientes").document("ID $codigoDeCliente").set(
                hashMapOf(
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "codigo_cliente" to codigoDeCliente,
                    "fecha_de_alta" to fechaDeAlta
                )
            )
        }

        return chequeo
    }

    fun obtenerListaDeClientes(): MutableList<Client> {
        return ClientRepository.get()

    }

    fun saberSiElClienteEsNuevo(nroDeClienteABuscar: Int): Boolean {
        val listaDeClientes = ClientRepository.get()
        val fechaActual = LocalDate.now()

        listaDeClientes.forEach() {
            if (it.codigo_cliente == nroDeClienteABuscar) {
                val fecha = LocalDate.parse(it.fecha_de_alta)
                if (fecha in fechaActual.minusMonths(6)..fechaActual) {
                    return true
                }
            }
        }
        return false
    }
}