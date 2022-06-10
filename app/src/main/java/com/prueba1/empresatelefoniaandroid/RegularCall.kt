package com.prueba1.empresatelefoniaandroid

class RegularCall(
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
                costoTotalDelCliente = if (ClientManager().saberSiElClienteEsNuevo(nroDeClienteABuscar)) {
                    it.duracion_llamada.times(0.02)
                } else {
                    it.duracion_llamada.times(0.05)
                }
            }
        }
        return costoTotalDelCliente
    }
}