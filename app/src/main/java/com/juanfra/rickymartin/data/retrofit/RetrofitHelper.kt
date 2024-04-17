package com.juanfra.rickymartin.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val baseUrl = "https://rickandmortyapi.com/api/"

    val retroMorty : MyService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(MyService::class.java)
    }
}