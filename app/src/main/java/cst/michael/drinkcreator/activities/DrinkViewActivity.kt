package cst.michael.drinkcreator.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import cst.michael.drinkcreator.Fragments.SingleDrinkFragment
import cst.michael.drinkcreator.R

class DrinkViewActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_drink_view)

        if(savedInstanceState == null) {
            val details: Fragment = SingleDrinkFragment()
            details.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(android.R.id.content, details).commit()
        }
    }
}
