package cst.michael.drinkcreator.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.firebase.FirebaseDBHelper
import kotlinx.android.synthetic.main.activity_create_drink.*
import android.widget.ArrayAdapter

class CreateDrinkActivity : AppCompatActivity() {
    private var drinkName = ""
    private val drinkFlavors = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        title = "Create a New Drink"

        setupBaseDrinkMenu()

        setupListeners()
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
            drinkFlavors.isEmpty() -> Toast.makeText(this, "Please add at least one flavor", Toast.LENGTH_SHORT).show()
            else -> dialogBuilder.setTitle("Drink Name")
                    .setView(drinkNameEditText)
                    .setPositiveButton("Done") { _, _ ->
                        drinkName = drinkNameEditText.text.toString()

                        if(drinkName != "") {
                            addDrink()
                            finish()
                        }

                    }
                    .setNeutralButton("cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
        }
    }

    private fun addDrink() {
        val helper = FirebaseDBHelper()
        val baseDrink = baseDrinkSpinner.selectedItem.toString()
        helper.addDrink(drinkName, drinkFlavors, baseDrink)
    }

    private fun setupListeners() {
        val flavorArray = resources.getStringArray(R.array.flavor_options_array)

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

        saveDrink.setOnClickListener {
            getDrinkName()
        }
    }

    private fun setupBaseDrinkMenu() {
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.base_drink_options_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        baseDrinkSpinner.adapter = adapter
    }
}
