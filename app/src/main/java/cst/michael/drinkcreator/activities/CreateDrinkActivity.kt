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
    private val drink = Drink()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)
        val saveButton = Button(this)

        saveButton.text = "Save Drink"
        saveButton.setOnClickListener {
            getDrinkName()
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

    fun getDrinkName() {
        val dialogBuilder =  AlertDialog.Builder(this)
        val drinkNameEditText = EditText(this)


        dialogBuilder.setTitle("Drink Name")
                .setView(drinkNameEditText)
                .setPositiveButton("Done") { dialog, which ->
                    drink.name = drinkNameEditText.text.toString()

                    val db = DrinkDbHelper(this).writableDatabase
                    val contentValues = ContentValues()
                    contentValues.put("DrinkName", drink.name)
                    contentValues.put("BaseDrink", drink.baseDrink)
                    contentValues.put("Flavors", drink.flavorsList.toString())
                    db.insert("Drinks", null, contentValues)

                    val intent = Intent(this, ListDrinksActivity::class.java)
                    startActivity(intent)
                }
                .setNeutralButton("cancel") { dialog, which ->
                    dialog.cancel()
                }
                .create()
                .show()
    }
}
