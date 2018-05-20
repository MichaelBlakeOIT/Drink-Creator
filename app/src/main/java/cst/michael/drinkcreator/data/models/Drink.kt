package cst.michael.drinkcreator.data.models

import java.io.Serializable

class Drink constructor(val name: String, val baseDrink: String, val flavorsList: MutableList<String>, val id: Int) : Serializable {
    fun getDrinkDescription(): String {
        var drinkString = "$this.baseDrink with "

        for(f in this.flavorsList) {
            drinkString += "$f, "
        }

        return drinkString
    }
}