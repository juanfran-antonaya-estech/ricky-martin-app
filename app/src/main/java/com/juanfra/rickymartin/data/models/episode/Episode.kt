package com.juanfra.rickymartin.data.models.episode


import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id") var id: Int = 0, // 20
    @SerializedName("name") var name: String = "", // Look Who's Purging Now
    @SerializedName("air_date") var airDate: String = "", // September 27, 2015
    @SerializedName("episode") var episode: String = "", // S02E09
    @SerializedName("characters") var characters: List<String> = listOf(),
    @SerializedName("url") var url: String = "", // https://rickandmortyapi.com/api/episode/20
    @SerializedName("created") var created: String = "" // 2017-11-10T12:56:35.772Z
)