package cst.michael.drinkcreator

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import cst.michael.drinkcreator.data.local.DrinkDbHelper
import cst.michael.drinkcreator.data.models.Drink
import cst.michael.drinkcreator.data.models.DrinkSQLObject
import kotlinx.android.synthetic.main.activity_create_drink.*

class CreateDrinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)
        val saveButton = Button(this)
        val drink = Drink()

        saveButton.text = "Save Drink"
        saveButton.setOnClickListener {
            val db = DrinkDbHelper(this).writableDatabase
            val contentValues = ContentValues()
            contentValues.put("BaseDrink", drink.baseDrink)
            contentValues.put("Flavors", drink.flavorsList.toString())
            db.insert("Drinks", null, contentValues)
        }

        drinkOptionsRadioGroup.setOnCheckedChangeListener{ group, checkedId ->
            val id = group.checkedRadioButtonId
            drink.baseDrink = findViewById<RadioButton>(id).text.toString()
        }

        for(f in flavorArray) {
            val checkbox = CheckBox(this)
            checkbox.text = f
            checkbox.setOnClickListener{
                drink.flavorsList.add(checkbox.text.toString())
            }
            drinkCreatorLayout.addView(checkbox)
        }

        drinkCreatorLayout.addView(saveButton)
    }
}
