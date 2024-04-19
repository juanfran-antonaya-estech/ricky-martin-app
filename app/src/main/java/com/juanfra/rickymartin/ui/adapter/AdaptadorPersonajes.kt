package com.juanfra.rickymartin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.databinding.HolderPersonajesBinding
import com.juanfra.rickymartin.ui.MyViewModel
import com.juanfra.rickymartin.ui.PicassoThings.ColorHelper
import com.juanfra.rickymartin.ui.listeners.CeldaClickListener
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation

class AdaptadorPersonajes(val personajes: ArrayList<Personaje>,val listener: CeldaClickListener) :
    RecyclerView.Adapter<AdaptadorPersonajes.MiCelda>() {
    //Your holder here
    inner class MiCelda(var binding: HolderPersonajesBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        lateinit var viewModel: MyViewModel
    }

    private lateinit var padre : ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiCelda {
        padre = parent
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderPersonajesBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    override fun getItemCount(): Int {
        return personajes.size
    }

    override fun onBindViewHolder(holder: MiCelda, position: Int) {
        val personaje = personajes[position]

        val helper = ColorHelper(personaje.image, holder.itemView.context)
        helper.init()

        with(holder.binding){
            val picasso = Picasso.get()
            if (personaje.status.equals("Dead")){
                picasso
                    .load(personaje.image)
                    .transform(GrayscaleTransformation())
                    .into(ivPersonajeLista)
                tvDead.visibility = TextView.VISIBLE
                tvMissing.visibility = TextView.GONE
            } else if (personaje.status.equals("Alive")) {
                Picasso.get()
                    .load(personaje.image)
                    .into(ivPersonajeLista)
                tvDead.visibility = TextView.GONE
                tvMissing.visibility = TextView.GONE
            } else {
                Picasso.get()
                    .load(personaje.image)
                    .transform(SepiaFilterTransformation(holder.itemView.context))
                    .transform(VignetteFilterTransformation(holder.itemView.context))
                    .into(ivPersonajeLista)
                tvDead.visibility = TextView.GONE
                tvMissing.visibility = TextView.VISIBLE
            }


            tvNombrePersonajeLista.text = personaje.name

            tvNombrePersonajeLista.setBackgroundColor(helper.MutedBG)
            tvNombrePersonajeLista.setTextColor(helper.MutedText)
            cardview.setCardBackgroundColor(helper.MutedBG)
        }

        holder.itemView.setOnClickListener{
            listener.clickEnCelda(personaje, position)


        }
    }

    fun refrescarListado(lista: List<Personaje>) {
        personajes.clear()
        if (lista != null){
            personajes.addAll(lista)
        }
        notifyItemRangeChanged(0, itemCount - 1)
    }


}