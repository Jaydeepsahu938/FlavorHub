package com.example.flavorhub.domain.dataModel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class RestaurantModel(
    val type: String,
    val features: List<Feature>,
)

data class Feature(
    val type: String,
    val properties: Properties,
)

data class Properties(
    val name: String?,
    val city: String?,
    val street: String?,
    val lon: Double,
    val lat: Double,
    val categories: List<String>,
    @SerializedName("opening_hours")
    val openingHours: String?,
    val ele: Long?,
    val catering: Catering?,
    @SerializedName("place_id")
    val placeId: String,
)


data class Catering(
    val cuisine: String,
    val stars: Double?,
    val reservation: String?,
)