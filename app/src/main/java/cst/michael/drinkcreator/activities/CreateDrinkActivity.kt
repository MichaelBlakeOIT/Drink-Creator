package cst.michael.drinkcreator.activities

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.local.DrinkDbHelper
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.activity_create_drink.*

class CreateDrinkActivity : AppCompatActivity() {
    //private val drink = Drink()
    private var drinkName = ""
    private val drinkFlavors = mutableListOf<String>()
    private var baseDrink = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)

        saveDrink.setOnClickListener {
            getDrinkName()
        }

        drinkOptionsRadioGroup.setOnCheckedChangeListener{ group, checkedId ->
            val id = group.checkedRadioButtonId
            baseDrink = findViewById<RadioButton>(id).text.toString()
        }

        for(f in flavorArray) {
            val checkbox = CheckBox(this)
            checkbox.text = f
            checkbox.setOnClickListener{
                drinkFlavors.add(checkbox.text.toString())
            }
            //place flavor list right above the button in the layout.
            drinkCreatorLayout.addView(checkbox)
        }
    }

    fun getDrinkName() {
        val dialogBuilder =  AlertDialog.Builder(this)
        val drinkNameEditText = EditText(this)

        dialogBuilder.setTitle("Drink Name")
                .setView(drinkNameEditText)
                .setPositiveButton("Done") { _, _ ->
                    val dbHelper = DrinkDbHelper(this)

                    drinkName = drinkNameEditText.text.toString()

                    dbHelper.insertDrink(dbHelper.writableDatabase, drinkName, baseDrink, drinkFlavors.toString())

                    val intent = Intent(this, ListDrinksActivity::class.java)
                    startActivity(intent)

                    finish()
                }
                .setNeutralButton("cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
    }
}
