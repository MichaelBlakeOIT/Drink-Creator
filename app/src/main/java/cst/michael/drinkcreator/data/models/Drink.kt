package cst.michael.drinkcreator.data.models

import java.io.Serializable

class Drink constructor(val name: String = "",
                        val baseDrink: String = "",
                        val flavorsList: MutableList<String> = mutableListOf(),
                        val userId: String = "") : Serializable {
    val likes = mutableMapOf<String, Boolean>()
}