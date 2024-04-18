package com.juanfra.rickymartin.ui.fragments

import AdaptadorPersonajes
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.databinding.FragmentFragmentEpisodeBinding
import com.juanfra.rickymartin.ui.MyViewModel

class FragmentEpisode : Fragment() {

    private val viewModel by activityViewModels<MyViewModel>()

    private var _binding: FragmentFragmentEpisodeBinding? = null

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

        viewModel.getEpisode().observe(viewLifecycleOwner){
            fillData(it)
        }
        viewModel.getCharacterList().observe(viewLifecycleOwner){
            fillAdapter(it)
        }

    }

    private fun fillAdapter(it: ArrayList<Personaje>) {
        val adaptadorPersonajes = AdaptadorPersonajes(it)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvEpisodes.adapter = adaptadorPersonajes
        binding.rvEpisodes.layoutManager = layoutManager
    }

    private fun fillData(episode: Episode) {
        binding.tvEpisodeName.text = episode.episode + ": " + episode.name
        binding.tvAirDate.text = episode.airDate

        viewModel.setCharacterList(episode.characters)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}