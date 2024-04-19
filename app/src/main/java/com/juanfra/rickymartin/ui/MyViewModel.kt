package com.juanfra.rickymartin.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanfra.rickymartin.data.Repositorio
import com.juanfra.rickymartin.data.models.LoadingPartner
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.data.models.characterlist.Info
import com.juanfra.rickymartin.data.models.characterlist.Location
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.data.models.location.LocationObject
import kotlinx.coroutines.launch

//https://rickandmortyapi.com/api/character/

class MyViewModel : ViewModel() {

    private val characterResultLiveData: MutableLiveData<CharacterPageResults> = MutableLiveData()
    private val personajeLiveData: MutableLiveData<Personaje> = MutableLiveData()
    private val episodioLiveData: MutableLiveData<Episode> = MutableLiveData()
    private val locationLiveData: MutableLiveData<com.juanfra.rickymartin.data.models.location.LocationObject> = MutableLiveData()
    private val characterListLiveData: MutableLiveData<ArrayList<Personaje>> = MutableLiveData()
    private val loadingPartnerLiveData: MutableLiveData<LoadingPartner> = MutableLiveData()
    private val nameLiveData: MutableLiveData<String> = MutableLiveData()

    private val repo by lazy {
        Repositorio()
    }

    fun setActualName(name: String?) {
        name?.let { nameLiveData.value = it }
    }

    fun getCharacterByPage(page: Int) {
        var nombre = ""

        nameLiveData.value?.let { nombre = it }

        viewModelScope.launch {
            val response = repo.getCharacterByPage(page, nombre)
            if (response.code() == 200) {
                val characterResponse = response.body()
                characterResponse?.let {
                    characterResultLiveData.postValue(it)
                }
            } else {
                    characterResultLiveData.postValue(CharacterPageResults(Info(0, 1, null, null), ArrayList<Personaje>()))
            }
        }

    }

    fun getCharacterResultLiveData(): MutableLiveData<CharacterPageResults> {
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

    fun getCharacter(): MutableLiveData<Personaje> {
        return personajeLiveData
    }

    fun getSelectedEpisode(episode: String): MutableLiveData<Episode> {
        val episodioLiveData = MutableLiveData<Episode>()
        viewModelScope.launch {
            val response = repo.getEpisodeByURL(episode)
            if (response.code() == 200) {
                response.body()?.let {
                    episodioLiveData.postValue(it)
                }
            }
        }
        return episodioLiveData
    }


    fun setEpisode(episodio: Episode?) {
        characterListLiveData.value?.clear()
        episodio?.let { episodioLiveData.postValue(it) }
        episodio?.characters?.let { setCharacterList(it) }
    }

    fun getEpisode(): MutableLiveData<Episode> {
        return episodioLiveData
    }

    fun setCharacterList(characters: List<String>) {
        characterListLiveData.value?.clear()
        viewModelScope.launch {
            val arrayMunecos = ArrayList<Personaje>()
            var contador = 1
            for (url in characters) {
                val loadingPartner = LoadingPartner(characters.size, contador)
                val id = url.lastIndexOf("/") + 1

                val response = repo.getCharacterById(url.substring(id).toInt())
                if (response.code() == 200) {
                    response.body()?.let {
                        arrayMunecos.add(it)
                        contador++
                        loadingPartnerLiveData.postValue(loadingPartner)
                        characterListLiveData.postValue(arrayMunecos)
                    }
                }
            }
        }
    }

    fun getCharacterList(): MutableLiveData<ArrayList<Personaje>> {
        return characterListLiveData
    }

    fun getLoadingPartner(): MutableLiveData<LoadingPartner> {
        return loadingPartnerLiveData
    }

    fun getLocation(): MutableLiveData<LocationObject> {
        return locationLiveData
    }

    fun setLocation(location: String) {
        characterListLiveData.value?.clear()
        viewModelScope.launch {
            val idLocation = location.lastIndexOf("/") + 1
            val response = repo.getLocationById(location.substring(idLocation).toInt())
            if (response.code() == 200){
                locationLiveData.postValue(response.body())
                response.body()?.residents?.let { setCharacterList(it) }
            }
        }
    }

}