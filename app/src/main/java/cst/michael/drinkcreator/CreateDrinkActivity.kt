package cst.michael.drinkcreator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.activity_create_drink.*

class CreateDrinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        //val drinkArray = resources.getStringArray(R.array.base_drink_options_array)
        //baseDrinkList.layoutManager = LinearLayoutManager(this)
        //baseDrinkList.adapter = RadioButtonAdapter(drinkArray.toCollection(ArrayList()), this)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)
        for(f in flavorArray) {
            val checkbox = CheckBox(this)
            checkbox.text = f
            drinkCreatorLayout.addView(checkbox)
        }

    }
}
