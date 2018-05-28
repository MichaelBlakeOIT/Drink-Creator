package cst.michael.drinkcreator.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import cst.michael.drinkcreator.data.models.Drink

class FirebaseDBHelper {
    fun getDBReference() : DatabaseReference {
        return FirebaseDatabase.getInstance().reference
        //return FirebaseDatabase.getInstance().getReference("drink-creator")
    }

    fun addDrink(name: String, flavors: MutableList<String>, base: String) {
        val key = getDBReference().child("drinks").push().key
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val drink = Drink(name, base, flavors, key!!, userId!!)
        getDBReference().child("drinks").child(key).setValue(drink)
    }

    fun addLike(drinkKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        getDBReference().child("likes").child(userId!!).child(drinkKey).setValue(true)
    }

    fun removeLike(drinkKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        getDBReference().child("likes").child(userId!!).child(drinkKey).removeValue()
    }
}