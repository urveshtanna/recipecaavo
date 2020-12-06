package com.urveshtanna.recipescaavo.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.urveshtanna.recipescaavo.R

class BindingAdapters {

    companion object {

        @BindingAdapter("recipePhoto")
        @JvmStatic
        fun setRecipePhoto(imgView: ImageView, imgUrl: String?) {
            Glide.with(imgView.context)
                .load(imgUrl)
                .placeholder(
                    ContextCompat.getDrawable(
                        imgView.context,
                        R.drawable.ic_recipe_placeholder
                    )
                )
                .into(imgView)
        }

    }

}