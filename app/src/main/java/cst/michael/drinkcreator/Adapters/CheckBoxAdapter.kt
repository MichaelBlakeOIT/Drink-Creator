package cst.michael.drinkcreator.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.checkbox_list_item.view.*

class CheckBoxAdapter(val items: ArrayList<String>, val context: Context, val drink: Drink) : RecyclerView.Adapter<CheckBoxAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.checkbox_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flavorsArray = context.resources.getStringArray(R.array.flavor_options_array)

        holder.drinkText.text = items[position]

        //holder.checkBox.setOnClickListener({_ -> if(holder.checkBox.isChecked) drink.flavorsList.add(flavorsArray[position]) else drink.flavorsList.remove(flavorsArray[position])})
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkText: TextView = itemView.TextviewForCheckbox
        val checkBox: CheckBox = itemView.listCheckbox
    }
}