package com.prueba1.empresatelefoniaandroid

import android.text.Editable

class CallsManager {
    fun costoDeLlamada(nroDeClienteABuscar: Int): Double {
        return Call().consultarCostoTotalDeUnCliente(nroDeClienteABuscar)
    }

    fun costoTotalDeLosClientes(): Double {
        return Call().consultarCostoTotalDeTodosLosClientes()
    }

}