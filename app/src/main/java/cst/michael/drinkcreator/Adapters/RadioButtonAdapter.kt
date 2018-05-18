package cst.michael.drinkcreator.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import cst.michael.drinkcreator.R
import cst.michael.drinkcreator.data.models.Drink
import kotlinx.android.synthetic.main.radiobutton_list_item.view.*

class RadioButtonAdapter(val items : ArrayList<String>, val context : Context, val drink: Drink) : RecyclerView.Adapter<RadioButtonAdapter.ViewHolder>() {
    public var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.radiobutton_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baseDrinkArray = context.resources.getStringArray(R.array.base_drink_options_array)



    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}