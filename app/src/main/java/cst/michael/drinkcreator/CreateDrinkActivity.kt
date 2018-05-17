package cst.michael.drinkcreator

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import cst.michael.drinkcreator.Adapters.CheckBoxAdapter
import cst.michael.drinkcreator.Adapters.RadioButtonAdapter
import kotlinx.android.synthetic.main.activity_create_drink.*

class CreateDrinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink)

        val drinkArray = resources.getStringArray(R.array.base_drink_options_array)
        baseDrinkList.layoutManager = LinearLayoutManager(this)
        baseDrinkList.adapter = RadioButtonAdapter(drinkArray.toCollection(ArrayList()), this)

        val flavorArray = resources.getStringArray(R.array.flavor_options_array)
        flavorList.layoutManager = LinearLayoutManager(this)
        flavorList.adapter = CheckBoxAdapter(flavorArray.toCollection(ArrayList()), this)

    }
}
