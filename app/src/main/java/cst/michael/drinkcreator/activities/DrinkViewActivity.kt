package cst.michael.drinkcreator.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import cst.michael.drinkcreator.Fragments.SingleDrinkFragment

class DrinkViewActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            val details: Fragment = SingleDrinkFragment()
            details.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(android.R.id.content, details).commit()
        }
    }
}
