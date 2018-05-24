package cst.michael.drinkcreator.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.local.DrinkDbHelper
import kotlinx.android.synthetic.main.activity_create_drink.*

class CreateDrinkActivity : AppCompatActivity() {
    private var drinkName = ""
    private val drinkFlavors = mutableListOf<String>()
    private var baseDrink = ""
    private var radioButtonId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)

        saveDrink.setOnClickListener {
            getDrinkName()
        }

        drinkOptionsRadioGroup.setOnCheckedChangeListener { group, _ ->
            val id = group.checkedRadioButtonId
            baseDrink = findViewById<RadioButton>(id).text.toString()
        }

        for (f in flavorArray) {
            val checkbox = CheckBox(this)
            checkbox.text = f
            checkbox.setOnClickListener {
                if (checkbox.isChecked) {
                    drinkFlavors.add(checkbox.text.toString())
                } else {
                    drinkFlavors.remove(checkbox.text.toString())
                }
            }
            //place flavor list right above the button in the layout.
            drinkCreatorLayout.addView(checkbox)
        }
    }

    /*override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("radioButtonId", drinkOptionsRadioGroup.checkedRadioButtonId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        radioButtonId = savedInstanceState?.getInt("radioButtonId")!!
        //drinkOptionsRadioGroup.check(id!!)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.isChecked = !item!!.isChecked
        return true
    }*/

    private fun getDrinkName() {
        val dialogBuilder = AlertDialog.Builder(this)
        val drinkNameEditText = EditText(this)

        when {
            drinkOptionsRadioGroup.checkedRadioButtonId == -1 -> Toast.makeText(this, "Please select a base drink", Toast.LENGTH_SHORT).show()
            drinkFlavors.isEmpty() -> Toast.makeText(this, "Please add at least one flavor", Toast.LENGTH_SHORT).show()
            else -> dialogBuilder.setTitle("Drink Name")
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
}
