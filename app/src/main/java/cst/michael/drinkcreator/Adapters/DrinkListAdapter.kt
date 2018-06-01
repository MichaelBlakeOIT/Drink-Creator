package cst.michael.drinkcreator.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.drink_row.view.*

/**
 * Created by Michael on 5/18/2018.
 */

class DrinkListAdapter(private val drinkList : List<Drink>, private val callback: (drinkId: Int) -> Unit) : RecyclerView.Adapter<DrinkListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(drinkList[position], position, callback)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.drink_row, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(drink: Drink, position: Int, callback: (drinkId: Int) -> Unit) {
            itemView.drinkName.text = drink.name
            itemView.drinkListBaseDrink.text = drink.baseDrink

            itemView.setOnClickListener {
                callback(position)
            }
        }
    }

    class FirebaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var clickListener = {}
        private var likeListener = {}
        val description = itemView.description
        private val userId = FirebaseAuth.getInstance().currentUser?.uid

        fun bindItems(drink: Drink) {
            itemView.drinkName.text = drink.name
            itemView.drinkListBaseDrink.text = drink.baseDrink
            itemView.leftLayout.setOnClickListener {
                clickListener()
            }
            if(userId != null) {
                itemView.heart.visibility = View.VISIBLE
                itemView.heart.setOnClickListener {
                    likeListener()
                }
                description.text = drink.flavorsList.toString().substring(1, drink.flavorsList.toString().length - 1).toLowerCase()

                if (drink.likes.containsKey(userId)) {
                    itemView.heart.setImageResource(R.drawable.ic_favorite_black_24dp)
                }
            }
        }

        fun setOnClickListener(callback: () -> Unit) {
            clickListener = callback
        }

        fun setOnLikeListener(callback: () -> Unit) {
            likeListener = callback
        }
    }
}