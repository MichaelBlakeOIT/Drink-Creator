package cst.michael.drinkcreator.Fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cst.michael.drinkcreator.Adapters.DrinkListAdapter
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.local.DrinkDbHelper
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.all_drinks.*

/**
 * Created by Michael on 5/18/2018.
 */

class DisplayAllDrinksFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.all_drinks, container, false)

        val drinks = arrayListOf<String>()

        val dbHelper = DrinkDbHelper(activity)

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
                drinks.add(cursor.getString(getColumnIndexOrThrow("DrinkName")))
            }

        }

        v?.findViewById<RecyclerView>(R.id.drinkListView)?.layoutManager = LinearLayoutManager(activity)

        v?.findViewById<RecyclerView>(R.id.drinkListView)?.adapter = DrinkListAdapter(drinks)

        return v
    }

    fun getInstance(): DisplayAllDrinksFragment {
        return DisplayAllDrinksFragment()
    }
}