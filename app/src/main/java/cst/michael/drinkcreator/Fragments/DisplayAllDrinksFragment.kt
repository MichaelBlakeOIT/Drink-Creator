package cst.michael.drinkcreator.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import cst.michael.drinkcreator.Adapters.DrinkListAdapter
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import android.support.v7.widget.DividerItemDecoration
import cst.michael.drinkcreator.data.firebase.FirebaseDBHelper
import kotlinx.android.synthetic.main.all_drinks.*
import kotlinx.android.synthetic.main.all_drinks.view.*
import kotlinx.android.synthetic.main.drink_row.*


/**
 * Created by Michael on 5/18/2018.
 */

class DisplayAllDrinksFragment : Fragment() {
    private lateinit var firebaseAdapter : FirebaseRecyclerAdapter<Drink, DrinkListAdapter.FirebaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.all_drinks, container, false)

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //dbHelper = DrinkDbHelper(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.drinkListView)

        setUpFirebaseAdapter(view)

        recyclerView.addItemDecoration(DividerItemDecoration(drinkListView.context, DividerItemDecoration.VERTICAL))

        //recyclerView.itemAnimator(DefaultItemAnimator())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = firebaseAdapter
    }

    private fun setUpFirebaseAdapter(view: View) {
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
                    val shouldExpand = holder.description.visibility == View.GONE

                    if (shouldExpand) {
                        holder.description.visibility = View.VISIBLE
                    } else {
                        holder.description.visibility = View.GONE
                    }
                }
                holder.setOnLikeListener {
                    val dbHelper = FirebaseDBHelper()

                    dbHelper.addLike(model.key)
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