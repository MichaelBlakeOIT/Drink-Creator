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


    }

}
