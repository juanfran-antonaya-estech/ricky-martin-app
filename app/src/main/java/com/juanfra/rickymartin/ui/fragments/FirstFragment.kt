package com.juanfra.rickymartin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.data.models.characterlist.CharacterPageResults
import com.juanfra.rickymartin.databinding.FragmentFirstBinding
import com.juanfra.rickymartin.ui.MyViewModel
import com.juanfra.rickymartin.ui.adapter.AdaptadorPersonajes
import com.juanfra.rickymartin.ui.listeners.CeldaClickListener

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var adaptador : AdaptadorPersonajes
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

        adaptador = AdaptadorPersonajes(ArrayList<Personaje>(), object : CeldaClickListener{
            override fun clickEnCelda(character: Personaje, position: Int) {
                viewModel.setCharacter(character.id)
                findNavController().navigate(R.id.initialFragment_to_Second_Fragment)
            }
        })
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerView.adapter = adaptador
        binding.recyclerView.layoutManager = layoutManager

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

        actualizarAdapter(rasultaos.results)

        binding.fabSearch.setOnClickListener{
            viewModel.setActualName(binding.tiName.text.toString())
            viewModel.getCharacterByPage(1)
        }
    }

    private fun actualizarAdapter(personajeList: List<Personaje>) {
        adaptador.refrescarListado(personajeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}