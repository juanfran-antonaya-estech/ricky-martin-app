package com.juanfra.rickymartin.data.models.characterlist


import com.google.gson.annotations.SerializedName

data class CharacterPageResults(
    @SerializedName("info") var info: Info,
    @SerializedName("results") var results: List<Personaje>
)