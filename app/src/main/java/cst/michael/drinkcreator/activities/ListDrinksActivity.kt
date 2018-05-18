package cst.michael.drinkcreator.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cst.michael.drinkcreator.R

import kotlinx.android.synthetic.main.activity_list_drinks.*

class ListDrinksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_drinks)

        fab.setOnClickListener {
            intent = Intent(this, CreateDrinkActivity::class.java)

            startActivity(intent)
        }
    }

}
