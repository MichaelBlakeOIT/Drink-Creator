package cst.michael.drinkcreator.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import cst.michael.drinkcreator.data.models.Drink
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



class FirebaseDBHelper {
    fun getDBReference() : DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    fun addDrink(name: String, flavors: MutableList<String>, base: String) {
        val key = getDBReference().child("drinks").push().key
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val drink = Drink(name, base, flavors, userId!!)
        getDBReference().child("drinks").child(key!!).setValue(drink)
    }

    fun addLike(drinkKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val like = mapOf(Pair(userId, true))

        getDBReference().child("likes").child(userId!!).child(drinkKey).setValue(true)
        getDBReference().child("drinks").child(drinkKey).child("likes").updateChildren(like)
    }

    fun removeLike(drinkKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        getDBReference().child("likes").child(userId!!).child(drinkKey).removeValue()

        getDBReference().child("drinks").child(drinkKey).child("likes").child(userId).removeValue()
    }

    fun getLikedDrinks() : MutableList<String> {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        val likes = getDBReference().child("likes").child(currentUser!!)
        val likeArray = mutableListOf<String>()

        likes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val key = dataSnapshot.key
                likeArray.add(key!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        return likeArray
    }
}