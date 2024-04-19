package com.juanfra.rickymartin.data.retrofit

import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.data.models.location.LocationObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyService {

    @GET("character/")
    suspend fun getCharsByPageAndName(
        @Query("page") page : Int,
        @Query("name") name : String
    ) : Response<CharacterPageResults>

    @GET("character/{id}")
    suspend fun getCharByID(
        @Path("id") id : Int
    ) : Response<Personaje>

    @GET("episode/{id}")
    suspend fun getEpisodeByID(
        @Path("id") id : Int
    ) : Response<Episode>

    @GET("location/{id}")
    suspend fun getLocationByID(
        @Path("id") id : Int
    ) : Response<LocationObject>

}