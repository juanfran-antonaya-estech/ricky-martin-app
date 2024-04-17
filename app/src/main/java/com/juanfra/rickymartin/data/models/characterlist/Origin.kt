package com.juanfra.rickymartin.data.models.characterlist


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name") var name: String, // Detoxifier
    @SerializedName("url") var url: String // https://rickandmortyapi.com/api/location/64
)