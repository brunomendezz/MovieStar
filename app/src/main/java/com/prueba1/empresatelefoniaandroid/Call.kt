package com.prueba1.empresatelefoniaandroid

open class Call(
    val codigo_cliente: Int = 0,
    val fecha_llamada: String = "",
    val hora_llamada: String = "",
    val duracion_llamada: Double = 0.0,
    var tipo_llamada: Char = 'N'
) {
    open fun consultarCostoTotalDeUnCliente(nroDeClienteABuscar: Int): Double {
        CallHistoryRepository.get().forEach() {
            if (nroDeClienteABuscar == it.codigo_cliente) {
                if (it.tipo_llamada == 'L') {
                    return it.consultarCostoTotalDeUnCliente(nroDeClienteABuscar)
                } else {
                    return it.consultarCostoTotalDeUnCliente(nroDeClienteABuscar) * 2
                }

            }
        }
        return 0.0
    }


    fun consultarCostoTotalDeTodosLosClientes(): Double {
        var costoTotal = 0.0
        CallHistoryRepository.get().forEach {
            costoTotal +=
                if (it.tipo_llamada == 'L') {
                    it.consultarCostoTotalDeUnCliente(it.codigo_cliente)
                } else {
                    it.consultarCostoTotalDeUnCliente(it.codigo_cliente) * 2
                }

        }
        return costoTotal
    }
}
