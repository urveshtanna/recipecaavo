package com.urveshtanna.recipescaavo.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.databinding.ItemCartBinding

class CartAdapter(var list: ArrayList<Recipe>, var onRemoveFromCart : (Recipe) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) {
            holder.binding.model = list[holder.bindingAdapterPosition]
            holder.binding.btnRemove.setOnClickListener {
                list[holder.bindingAdapterPosition].quantity = list[holder.bindingAdapterPosition].quantity - 1
                val recipe = list[holder.bindingAdapterPosition]
                list.removeAt(holder.bindingAdapterPosition)
                notifyItemRemoved(holder.bindingAdapterPosition)
                onRemoveFromCart.invoke(recipe)
            }
        }
    }

    fun addAll(tempList: List<Recipe>?){
        if(tempList != null) {
            list = arrayListOf()
            list.addAll(tempList)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(var binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

}