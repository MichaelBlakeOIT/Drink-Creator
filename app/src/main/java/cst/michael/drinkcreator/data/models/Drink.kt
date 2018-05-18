package cst.michael.drinkcreator.data.models

class Drink(_baseDrink: String) {
    val baseDrink = _baseDrink
    val flavorsList = mutableListOf<String>()
}