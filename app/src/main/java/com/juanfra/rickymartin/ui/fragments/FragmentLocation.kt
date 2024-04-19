package com.juanfra.rickymartin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.databinding.FragmentLocationBinding
import com.juanfra.rickymartin.ui.MyViewModel
import com.juanfra.rickymartin.ui.adapter.AdaptadorPersonajes
import com.juanfra.rickymartin.ui.listeners.CeldaClickListener


class FragmentLocation : Fragment() {

    private lateinit var binding : FragmentLocationBinding
    private lateinit var adaptador: AdaptadorPersonajes

    val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        adaptador = AdaptadorPersonajes(ArrayList(), object : CeldaClickListener {
            override fun clickEnCelda(character: Personaje, position: Int) {
                viewModel.setCharacter(character.id)
                findNavController().navigate(R.id.action_fragmentLocation_to_SecondFragment)
            }
        })
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.rvLocation.adapter = adaptador
        binding.rvLocation.layoutManager = layoutManager


        viewModel.getLocation().observe(viewLifecycleOwner) {
            binding.tvLocationName.text = it.name
            binding.tvType.text = it.type
            binding.tvDimension.text = it.dimension
            binding.tvLocationResidents.text = "Residentes de ${it.name}"
        }

        viewModel.getCharacterList().observe(viewLifecycleOwner) {
            adaptador.refrescarListado(it)
        }

        viewModel.getLoadingPartner().observe(viewLifecycleOwner) {
            if (it.total == binding.locationPb.max){
                binding.locationPb.progress = it.current
            } else {
                binding.locationPb.max = it.total
                binding.locationPb.progress = it.current
            }

            if (it.current == binding.locationPb.max){
                binding.locationPb.alpha = 0F
            } else {
                binding.locationPb.alpha = 1F
            }
        }
    }


}