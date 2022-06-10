package com.prueba1.empresatelefoniaandroid

object CallHistoryRepository {
    private var callsHistory: MutableList<Call> = mutableListOf()

    init {
        callsHistory.add(WeekendCall(0, "2022-01-01", "10:30", 351.2, 'L'))
        callsHistory.add(NightCall(1, "2021-11-12", "22:30", 132.0, 'L'))
        callsHistory.add(RegularCall(2, "2022-04-25", "18:09", 1402.3, 'I'))
        callsHistory.add(RegularCall(3, "2022-03-10", "02:11", 105.5, 'L'))
        callsHistory.add(RegularCall(4, "2021-02-12", "02:11", 105.5, 'L'))
    }

    fun get(): MutableList<Call> {
        return callsHistory
    }

}