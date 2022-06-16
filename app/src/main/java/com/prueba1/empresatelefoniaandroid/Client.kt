package com.prueba1.empresatelefoniaandroid

import java.io.Serializable


data class Client(
    val apellido: String = "",
    val codigo_cliente: Int = 0,
    val fecha_de_alta: String = "",
    val nombre: String = ""
) : Serializable


