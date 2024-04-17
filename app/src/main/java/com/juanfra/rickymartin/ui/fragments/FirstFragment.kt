package com.juanfra.rickymartin.ui.fragments

import AdaptadorPersonajes
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.databinding.FragmentFirstBinding
import com.juanfra.rickymartin.ui.MyViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var currentPage = 1

    private var totalPage = 0

    private val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacterByPage(currentPage)
        AdaptadorPersonajes.viewModel = viewModel

        viewModel.getCharacterResultLiveData().observe(viewLifecycleOwner, observer)
    }

    val observer = Observer<CharacterPageResults> { rasultaos ->
        val info = rasultaos.info

        info?.let {irfomacion ->
            totalPage = irfomacion.pages
        }

        val characters = rasultaos.results

        if (info.next.isNullOrEmpty()){
            binding.ivLeft.visibility = ImageView.GONE
        } else {
            binding.ivLeft.visibility = ImageView.VISIBLE
        }

        if (info.prev.isNullOrEmpty()){
            binding.ivLeft.visibility = ImageView.GONE
        } else {
            binding.ivLeft.visibility = ImageView.VISIBLE
        }

        binding.ivLeft.setOnClickListener{
            currentPage--
            viewModel.getCharacterByPage(currentPage)
        }

        binding.ivRight.setOnClickListener{
            currentPage++
            viewModel.getCharacterByPage(currentPage)
        }

        llenarAdaptador(rasultaos.results)
    }

    private fun llenarAdaptador(personajeList: List<Personaje>) {
        val adaptadorPersonajes = AdaptadorPersonajes(ArrayList(personajeList))
        val layoutManager = GridLayoutManager(requireContext(), 2)

        binding.recyclerView.adapter = adaptadorPersonajes
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}