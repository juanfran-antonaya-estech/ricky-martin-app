package com.juanfra.rickymartin.data

import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.data.models.characterlist.Location
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.data.models.location.LocationObject
import com.juanfra.rickymartin.data.retrofit.RetrofitHelper
import retrofit2.Response

class Repositorio {

    val retromorty = RetrofitHelper.retroMorty

    suspend fun getCharacterByPage(page: Int, name : String): Response<CharacterPageResults> {
        return retromorty.getCharsByPageAndName(page, name)
    }

    suspend fun getCharacterById(id: Int): Response<Personaje> {
        return retromorty.getCharByID(id)
    }

    suspend fun getEpisodeByURL(episode: String): Response<Episode> {
        val episodeIdIndex = episode.lastIndexOf("/")
        val id = episode.substring(episodeIdIndex + 1)
        return retromorty.getEpisodeByID(id.toInt())
    }

    suspend fun getLocationById(id: Int): Response<LocationObject> {
        return retromorty.getLocationByID(id)
    }
}