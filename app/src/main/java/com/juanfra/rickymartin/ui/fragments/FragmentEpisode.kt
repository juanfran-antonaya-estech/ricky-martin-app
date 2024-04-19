package com.juanfra.rickymartin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.databinding.FragmentFragmentEpisodeBinding
import com.juanfra.rickymartin.ui.MyViewModel
import com.juanfra.rickymartin.ui.adapter.AdaptadorPersonajes
import com.juanfra.rickymartin.ui.listeners.CeldaClickListener

class FragmentEpisode : Fragment() {

    private val viewModel by activityViewModels<MyViewModel>()

    private var _binding: FragmentFragmentEpisodeBinding? = null

    private lateinit var adaptador: AdaptadorPersonajes

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adaptador = AdaptadorPersonajes(ArrayList(), object : CeldaClickListener{
            override fun clickEnCelda(character: Personaje, position: Int) {
                AdaptadorPersonajes.viewModel.setCharacter(character.id)
                findNavController().navigate(R.id.action_fragmentEpisode_to_SecondFragment)
            }
        })
        AdaptadorPersonajes.viewModel = viewModel
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.rvEpisodes.adapter = adaptador
        binding.rvEpisodes.layoutManager = layoutManager

        viewModel.getEpisode().observe(viewLifecycleOwner) {
            fillData(it)
        }
        viewModel.getCharacterList().observe(viewLifecycleOwner) { arrayList ->
            fillAdapter(arrayList)
        }
        viewModel.getLoadingPartner().observe(viewLifecycleOwner) {
            if (it.total == binding.progressBar2.max){
                binding.progressBar2.progress = it.current
            } else {
                binding.progressBar2.max = it.total
                binding.progressBar2.progress = it.current
            }

            if (it.current == binding.progressBar2.max){
                binding.progressBar2.alpha = 0F
            } else {
                binding.progressBar2.alpha = 1F
            }
        }

    }

    private fun fillAdapter(it: ArrayList<Personaje>) {
        adaptador.refrescarListado(it)
    }

    private fun fillData(episode: Episode) {
        binding.tvEpisodeName.text = episode.episode + ": " + episode.name
        binding.tvAirDate.text = episode.airDate
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}