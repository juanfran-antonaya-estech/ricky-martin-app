package com.juanfra.rickymartin.data.models.characterlist


import com.google.gson.annotations.SerializedName

data class Personaje(
    @SerializedName("id") var id: Int, // 361
    @SerializedName("name") var name: String, // Toxic Rick
    @SerializedName("status") var status: String, // Dead
    @SerializedName("species") var species: String, // Humanoid
    @SerializedName("type") var type: String, // Rick's toxic side
    @SerializedName("gender") var gender: String, // Male
    @SerializedName("origin") var origin: Origin,
    @SerializedName("location") var location: Location,
    @SerializedName("image") var image: String, // https://rickandmortyapi.com/api/character/avatar/361.jpeg
    @SerializedName("episode") var episode: List<String>,
    @SerializedName("url") var url: String, // https://rickandmortyapi.com/api/character/361
    @SerializedName("created") var created: String // 2018-01-10T18:20:41.703Z
)