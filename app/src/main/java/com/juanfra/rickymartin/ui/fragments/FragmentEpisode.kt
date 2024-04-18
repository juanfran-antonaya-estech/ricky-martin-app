package com.juanfra.rickymartin.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.episode.Episode
import com.juanfra.rickymartin.databinding.FragmentFragmentEpisodeBinding
import com.juanfra.rickymartin.databinding.FragmentSecondBinding
import com.juanfra.rickymartin.ui.MyViewModel

class FragmentEpisode : Fragment() {

    private val viewModel by activityViewModels<MyViewModel>()

    private var _binding: FragmentFragmentEpisodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getEpisode().observe(viewLifecycleOwner){
            fillData(it)
        }

    }

    private fun fillData(episode: Episode) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}