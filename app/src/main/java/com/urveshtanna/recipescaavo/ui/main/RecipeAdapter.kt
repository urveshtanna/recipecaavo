package com.urveshtanna.recipescaavo.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.recipescaavo.R
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.databinding.ItemRecipeBinding
import com.urveshtanna.recipescaavo.databinding.ItemRecipeFooterBinding

class RecipeAdapter(var recipeList: ArrayList<Recipe>, private val onRecipeClick: (Recipe, Boolean) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    public val VIEW_TYPE_BODY: Int = 1;
    public val VIEW_TYPE_HEAD: Int = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_HEAD) {
            val binding =
                ItemRecipeFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return Footer(binding)
        } else {
            val binding =
                ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return Holder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= recipeList.size) {
            return VIEW_TYPE_HEAD
        } else {
            return VIEW_TYPE_BODY
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder && getItemViewType(holder.bindingAdapterPosition) == VIEW_TYPE_BODY) {
            holder.binding.model = recipeList.get(holder.bindingAdapterPosition)
            holder.binding.btnAddToCart.setOnClickListener {
                if (recipeList.get(holder.bindingAdapterPosition).quantity > 0) {
                    recipeList.get(holder.bindingAdapterPosition).quantity =
                        recipeList.get(holder.bindingAdapterPosition).quantity - 1
                } else {
                    recipeList.get(holder.bindingAdapterPosition).quantity =
                        recipeList.get(holder.bindingAdapterPosition).quantity + 1
                }
                onRecipeClick.invoke(recipeList.get(holder.bindingAdapterPosition), false)
                notifyItemChanged(holder.bindingAdapterPosition)
            }

            holder.binding.btnInfo.setOnClickListener {
                onRecipeClick.invoke(recipeList.get(holder.bindingAdapterPosition), true)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size + 1
    }

    fun addRecipes(list: List<Recipe>?) {
        if (list != null) {
            recipeList = arrayListOf()
            recipeList.addAll(list)
            notifyDataSetChanged()
        }
    }

    public class Holder(var binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    public class Footer(var binding: ItemRecipeFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}