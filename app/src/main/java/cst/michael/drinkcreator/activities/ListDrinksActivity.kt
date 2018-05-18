package cst.michael.drinkcreator.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.local.DrinkDbHelper

import kotlinx.android.synthetic.main.activity_list_drinks.*
import kotlinx.android.synthetic.main.content_list_drinks.*
import kotlinx.android.synthetic.main.content_list_drinks.view.*

class ListDrinksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_drinks)
        setSupportActionBar(toolbar)

        val dbHelper = DrinkDbHelper(this)

        val db = dbHelper.readableDatabase

        val projection = arrayOf("Id", "DrinkName", "BaseDrink", "Flavors")

        val cursor = db.query(
                "Drinks",
                projection,
                null,
                null,
                null,
                null,
                null
        )

        with(cursor) {
            while (moveToNext()) {
                val textView = TextView(applicationContext)
                textView.text = cursor.getString(getColumnIndexOrThrow("DrinkName"))

                drinkListView.addFooterView(textView)
            }
            drinkListView.invalidateViews()
        }
    }

}
