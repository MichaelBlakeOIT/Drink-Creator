package cst.michael.drinkcreator.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cst.michael.drinkcreator.R
import kotlinx.android.synthetic.main.checkbox_list_item.view.*

class CheckBoxAdapter(val items : ArrayList<String>, val context : Context) : RecyclerView.Adapter<CheckBoxAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.checkbox_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.drinkText.text = items[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkText: TextView = itemView.TextviewForCheckbox
    }
}