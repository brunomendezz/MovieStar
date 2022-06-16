package com.prueba1.empresatelefoniaandroid

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

object ClientRepository {
    private val db = FirebaseFirestore.getInstance()
    private var clients: MutableList<Client> = mutableListOf()

    init {

        db.collection("Clientes")
            .get()
            .addOnSuccessListener { resultado ->
                for (document in resultado) {
                    val client = document.toObject<Client>()
                    clients.add(client)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


        clients.add(Client("Odduy",1,"2021-01-01","Mark", ))
        clients.add(Client("Jackson",2, "2022-04-15","John" ))
        clients.add(Client("Larrson",3, "2022-01-01","Elizabeth" ))
        clients.add(Client("Kroscen",4,   "2020-01-22","Stefany"))
    }

    fun get(): MutableList<Client> {

        return clients
    }

}