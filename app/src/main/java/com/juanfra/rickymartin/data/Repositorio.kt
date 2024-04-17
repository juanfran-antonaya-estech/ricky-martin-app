package com.juanfra.rickymartin.data

import android.app.Person
import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.retrofit.RetrofitHelper
import retrofit2.Response

class Repositorio {

    val retromorty = RetrofitHelper.retroMorty

    suspend fun getCharacterByPage(page: Int): Response<CharacterPageResults> {
        return retromorty.getCharsByPage(page)
    }

    suspend fun getCharacterById(id: Int): Response<Personaje> {
        return retromorty.getCharByID(id)
    }
}