package cst.michael.drinkcreator.data.models

class Drink {
    var name = ""
    var baseDrink = ""
    val flavorsList = mutableListOf<String>()

    fun getDrinkDescription(): String {
        var drinkString = "$baseDrink with "

        for(f in flavorsList) {
            drinkString += "$f, "
        }

        return drinkString
    }
}