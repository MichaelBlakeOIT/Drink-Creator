package cst.michael.drinkcreator.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import cst.michael.drinkcreator.activities.ListDrinksActivity
import cst.michael.drinkcreator.data.firebase.FirebaseDBHelper
import kotlinx.android.synthetic.main.all_drinks.*


/**
 * Created by Michael on 5/18/2018.
 */

class DisplayAllDrinksFragment : Fragment() {
    private lateinit var firebaseAdapter : FirebaseRecyclerAdapter<Drink, DrinkListAdapter.FirebaseViewHolder>
    private val dbHelper = FirebaseDBHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.all_drinks, container, false)

        (activity as ListDrinksActivity).fragmentListener = {
            firebaseAdapter.notifyDataSetChanged()
        }

        //val likes = dbHelper.getLikedDrinks()

        //Toast.makeText(activity, likes.size.toString(), Toast.LENGTH_LONG).show()

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //dbHelper = DrinkDbHelper(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.drinkListView)

        setUpFirebaseAdapter()

        recyclerView.addItemDecoration(DividerItemDecoration(drinkListView.context, DividerItemDecoration.VERTICAL))

        //recyclerView.itemAnimator(DefaultItemAnimator())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = firebaseAdapter
    }

    private fun setUpFirebaseAdapter() {
        val ref = FirebaseDatabase.getInstance().reference

        val drinksQuery = ref
                .child("drinks")
                .limitToLast(20)

        val drinksOptions = FirebaseRecyclerOptions.Builder<Drink>()
                .setQuery(drinksQuery, Drink::class.java)
                .build()


        firebaseAdapter = object : FirebaseRecyclerAdapter<Drink, DrinkListAdapter.FirebaseViewHolder>(drinksOptions) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkListAdapter.FirebaseViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.drink_row, parent, false)

                return DrinkListAdapter.FirebaseViewHolder(view)
            }

            override fun onBindViewHolder(holder: DrinkListAdapter.FirebaseViewHolder, position: Int, model: Drink) {
                holder.bindItems(model)
                holder.setOnClickListener {
                    val shouldExpand = holder.description.visibility == View.GONE

                    //getRef(position).key

                    if (shouldExpand) {
                        holder.description.visibility = View.VISIBLE
                    } else {
                        holder.description.visibility = View.GONE
                    }
                }
                holder.setOnLikeListener {
                    val key = getRef(position).key
                    dbHelper.addLike(key!!)
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

    fun getLikedDrinks() : MutableList<String> {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        val likes = dbHelper.getDBReference().child("likes").child(currentUser!!)
        val likeArray = mutableListOf<String>()

        /*likes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val key = dataSnapshot.key
                likeArray.add(key!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })*/



        return likeArray
    }
}