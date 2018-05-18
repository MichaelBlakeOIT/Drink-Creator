package cst.michael.drinkcreator.data.models

class Drink {
    var baseDrink = ""
    val flavorsList = mutableListOf<String>()

    override fun toString(): String {
        var drinkString = "$baseDrink with "

        for(f in flavorsList) {
            drinkString += "$f, "
        }

        return drinkString
    }
}