package com.juanfra.rickymartin.data.models.location


import com.google.gson.annotations.SerializedName

data class LocationObject(
    @SerializedName("id") var id: Int = 0, // 20
    @SerializedName("name") var name: String = "", // Earth (Replacement Dimension)
    @SerializedName("type") var type: String = "", // Planet
    @SerializedName("dimension") var dimension: String = "", // Replacement Dimension
    @SerializedName("residents") var residents: List<String> = listOf(),
    @SerializedName("url") var url: String = "", // https://rickandmortyapi.com/api/location/20
    @SerializedName("created") var created: String = "" // 2017-11-18T19:33:01.173Z
)