package com.juanfra.rickymartin.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanfra.rickymartin.data.Repositorio
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.data.models.episode.Episode
import kotlinx.coroutines.launch

//https://rickandmortyapi.com/api/character/

class MyViewModel : ViewModel() {

    private val characterResultLiveData : MutableLiveData<CharacterPageResults> = MutableLiveData()
    private val personajeLiveData : MutableLiveData<Personaje> = MutableLiveData()
    private val episodioLiveData : MutableLiveData<Episode> = MutableLiveData()

    private val repo by lazy {
        Repositorio()
    }

    fun getCharacterByPage(page: Int) {


        viewModelScope.launch {
            val response = repo.getCharacterByPage(page)
            if(response.code() == 200) {
                val characterResponse = response.body()
                characterResponse?.let {
                    characterResultLiveData.postValue(it)
                }
            }
        }

    }

    fun getCharacterResultLiveData() : MutableLiveData<CharacterPageResults>{
        return characterResultLiveData
    }

    fun setCharacter(id: Int) {

        viewModelScope.launch {
            val response = repo.getCharacterById(id)
            if (response.code() == 200) {
                val characterResponse = response.body()
                characterResponse?.let {
                    personajeLiveData.postValue(it)
                }
            }
        }
    }

    fun getCharacter() : MutableLiveData<Personaje> {
        return personajeLiveData
    }

    fun getSelectedEpisode(episode: String): MutableLiveData<Episode> {
            val episodioLiveData = MutableLiveData<Episode>()
        viewModelScope.launch {
            val response = repo.getEpisodeByURL(episode)
            if (response.code() == 200){
                response.body()?.let {
                    episodioLiveData.postValue(it)
                }
            }
        }
            return episodioLiveData
    }

    fun setEpisode(episodio: Episode?) {
        episodio?.let { episodioLiveData.postValue(it) }
    }

    fun getEpisode() : MutableLiveData<Episode> {
        return episodioLiveData
    }

}