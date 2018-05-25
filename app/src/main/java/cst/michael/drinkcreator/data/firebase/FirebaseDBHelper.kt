package cst.michael.drinkcreator.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import cst.michael.drinkcreator.data.models.Drink

class FirebaseDBHelper {
    fun getDBReference() : DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    fun addDrink(name: String, flavors: MutableList<String>, base: String) {
        val key = getDBReference().child("drinks").push().key
        val drink = Drink(name, base, flavors, key!!)
        getDBReference().child("drinks").child(key).setValue(drink)
    }
}