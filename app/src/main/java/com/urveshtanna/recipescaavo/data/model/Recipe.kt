package com.urveshtanna.recipescaavo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Recipe(
    @PrimaryKey @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("label") val label: String? = null,
    @SerializedName("price") val price: String? = null,
    @SerializedName("description") val description: String? = null,
) {

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0
}