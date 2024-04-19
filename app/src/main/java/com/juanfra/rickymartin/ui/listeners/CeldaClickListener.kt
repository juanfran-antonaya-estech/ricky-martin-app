package com.juanfra.rickymartin.ui.listeners

import com.juanfra.rickymartin.data.models.characterlist.Personaje

interface CeldaClickListener {
    fun clickEnCelda(character: Personaje, position: Int)
}