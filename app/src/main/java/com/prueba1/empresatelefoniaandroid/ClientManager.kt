package com.prueba1.empresatelefoniaandroid

class ClientManager {
    fun darDeAltaCliente(nombre: String, apellido: String): Boolean {
        return ClientRepository.darDeAltaCliente(nombre, apellido)
    }

    fun obtenerListaDeClientes(): MutableList<Client> {
        return ClientRepository.get()

    }
    fun saberSiElClienteEsNuevo(nroDeClienteABuscar: Int): Boolean {
        return Client().consultarFechaDeAltaDelCliente(nroDeClienteABuscar)
    }

}