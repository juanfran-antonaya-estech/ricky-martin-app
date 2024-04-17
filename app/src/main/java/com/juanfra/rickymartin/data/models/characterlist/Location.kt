package com.juanfra.rickymartin.data.models.characterlist


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name") var name: String, // Earth (Replacement Dimension)
    @SerializedName("url") var url: String // https://rickandmortyapi.com/api/location/20
)