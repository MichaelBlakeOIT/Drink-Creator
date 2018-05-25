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
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import cst.michael.drinkcreator.Adapters.DrinkListAdapter
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import cst.michael.drinkcreator.activities.DrinkViewActivity
import java.io.Serializable


/**
 * Created by Michael on 5/18/2018.
 */

class DisplayAllDrinksFragment : Fragment() {
    private lateinit var firebaseAdapter : FirebaseRecyclerAdapter<Drink, DrinkListAdapter.FirebaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.all_drinks, container, false)

        setUpFirebaseAdapter()

        v?.findViewById<RecyclerView>(R.id.drinkListView)?.layoutManager = LinearLayoutManager(activity)
        v?.findViewById<RecyclerView>(R.id.drinkListView)?.adapter = firebaseAdapter

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //dbHelper = DrinkDbHelper(context)
    }

    private fun setUpFirebaseAdapter() {
        val query = FirebaseDatabase.getInstance()
                .reference
                .child("drinks")
                .limitToLast(20)

        val options = FirebaseRecyclerOptions.Builder<Drink>()
                .setQuery(query, Drink::class.java)
                .build()

        firebaseAdapter = object : FirebaseRecyclerAdapter<Drink, DrinkListAdapter.FirebaseViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkListAdapter.FirebaseViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.drink_row, parent, false)

                return DrinkListAdapter.FirebaseViewHolder(view)
            }

            override fun onBindViewHolder(holder: DrinkListAdapter.FirebaseViewHolder, position: Int, model: Drink) {
                holder.bindItems(model)
                holder.setOnClickListener {
                    //if(activity?.larg == null) {
                        val intent = Intent(activity, DrinkViewActivity::class.java)
                        intent.putExtra("drink", model as Serializable)
                        startActivity(intent)
                    //}
                    /*else {
                        val bundle = Bundle()
                        bundle.putSerializable("drink", drinks[it])

                        val fragObj: Fragment = SingleDrinkFragment()
                        fragObj.arguments = bundle

                        fragmentManager?.beginTransaction()?.replace(R.id.singleDrinkFragment, fragObj)?.commit()
                    }*/
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        firebaseAdapter.stopListening()
    }
}