package com.prueba1.empresatelefoniaandroid

class NightCall(
    codigo_cliente: Int,
    fecha_llamada: String,
    hora_llamada: String,
    duracion_llamada: Double,
    tipo_llamada: Char
) : Call(codigo_cliente, fecha_llamada, hora_llamada, duracion_llamada, tipo_llamada) {

    override fun consultarCostoTotalDeUnCliente(nroDeClienteABuscar: Int): Double {
        var costoTotalDelCliente = 0.0
        CallHistoryRepository.get().forEach {
            if (it.codigo_cliente == nroDeClienteABuscar) {
                costoTotalDelCliente = it.duracion_llamada.times(0.02)
            }
        }
        return costoTotalDelCliente
    }

}