package cst.michael.drinkcreator.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.single_drink.view.*

class SingleDrinkFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.single_drink, container, false)

        if (arguments != null) {
            val drink = arguments?.get("drink") as Drink
            val flavors = drink.flavorsList.toString().substring(1, drink.flavorsList.toString().length - 1).toLowerCase()

            v.singleDrinkName.text = drink.name
            v.SingleDrinkBase.text = drink.baseDrink
            v.SingleDrinkFlavors.text = flavors

            v.shareDrink.setOnClickListener {
                val tweet = "Try the ${drink.name}. It's a ${drink.baseDrink} with $flavors flavoring."
                val url = "http://www.twitter.com/intent/tweet?text=$tweet"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }

        return v
    }
}