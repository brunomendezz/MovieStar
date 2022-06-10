package com.prueba1.empresatelefoniaandroid

object ClientRepository {
    private var clients: MutableList<Client> = mutableListOf()

    init {
        clients.add(Client(1, "Mark", "Odduy", "2021-01-01"))
        clients.add(Client(2, "John", "Jackson", "2022-04-15"))
        clients.add(Client(3, "Elizabeth", "Larrson", "2022-01-01"))
        clients.add(Client(4, "Stefany", "Kroscen", "2020-01-22"))
    }

    fun get(): MutableList<Client> {

        return clients
    }

    //REGISTRAR UN NUEVO CLIENTE
    fun darDeAltaCliente(nombre: String, apellido: String): Boolean {
        var codigoDeCliente = clients.size + 1
        var nuevoCliente = Client(codigoDeCliente, nombre, apellido)
        return clients.add(nuevoCliente)
    }
}