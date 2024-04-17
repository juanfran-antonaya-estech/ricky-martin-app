package com.juanfra.rickymartin.data.models.characterlist


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count") var count: Int, // 826
    @SerializedName("pages") var pages: Int, // 42
    @SerializedName("next") var next: String?, // https://rickandmortyapi.com/api/character/?page=20
    @SerializedName("prev") var prev: String? // https://rickandmortyapi.com/api/character/?page=18
)