package cst.michael.drinkcreator.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cst.michael.drinkcreator.Adapters.DrinkListAdapter
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.activities.DrinkViewActivity
import cst.michael.drinkcreator.data.local.DrinkDbHelper
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.activity_list_drinks.*
import java.io.Serializable

/**
 * Created by Michael on 5/18/2018.
 */

class DisplayAllDrinksFragment : Fragment() {

    private lateinit var dbHelper: DrinkDbHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.all_drinks, container, false)

        val drinks = arrayListOf<Drink>()

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
                with(cursor) {
                    val name = getString(getColumnIndexOrThrow("DrinkName"))
                    val id = getInt(getColumnIndexOrThrow("Id"))
                    val flavors = getString(getColumnIndexOrThrow("Flavors"))
                    val baseDrink = getString(getColumnIndexOrThrow("BaseDrink"))
                    val flavorsList = flavors.substring(1, flavors.length - 1).split(", ").toMutableList()
                    val drink = Drink(name, baseDrink, flavorsList, id)
                    drinks.add(drink)
                }
            }
        }

        v?.findViewById<RecyclerView>(R.id.drinkListView)?.layoutManager = LinearLayoutManager(activity)

        v?.findViewById<RecyclerView>(R.id.drinkListView)?.adapter = DrinkListAdapter(drinks,  {

            if(activity?.largeScreenLayout == null) {
                val intent = Intent(activity, DrinkViewActivity::class.java)
                intent.putExtra("drink", drinks[it] as Serializable)
                startActivity(intent)
            }
            else {
                val bundle = Bundle()
                bundle.putSerializable("drink", drinks[it])

                val fragObj: Fragment = SingleDrinkFragment()
                fragObj.arguments = bundle

                fragmentManager?.beginTransaction()?.replace(R.id.singleDrinkFragment, fragObj)?.commit()
            }
        })


        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbHelper = DrinkDbHelper(context)
    }
}