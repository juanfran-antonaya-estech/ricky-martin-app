package com.juanfra.rickymartin.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.databinding.FragmentSecondBinding
import com.juanfra.rickymartin.ui.PicassoThings.ColorHelper
import com.juanfra.rickymartin.ui.MyViewModel
import com.squareup.picasso.Picasso
import java.time.Instant
import java.util.Date

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val viewModel by activityViewModels<MyViewModel>()

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharacter().observe(viewLifecycleOwner) {
            it?.let { fillPagina(it) }
            Log.d("El moñeco", it.toString())
        }
    }

    private fun fillPagina(character: Personaje) {
        binding.tvCharacterName.text = character.name
        binding.tvGender.text = character.gender
        binding.tvStatus.text = character.status
        binding.btUbicacion.text = character.location.name
        binding.btOrigen.text = character.origin.name
        binding.tvEpisodesTitle.text = "Episodios en los que aparece ${character.name}"

        val fecha = Date.from(Instant.parse(character.created))
        binding.tvCreationDate.text = fecha.toString()


        val helper = ColorHelper(character.image, requireContext())
        helper.init()

        Picasso.get()
            .load(character.image)
            .into(binding.ivCharacterFace)

        binding.tvCharacterName.setTextColor(helper.DarkMutedText)
        binding.tvCharacterName.setBackgroundColor(helper.DarkMutedBG)
        binding.tvEpisodesTitle.setTextColor(helper.DarkMutedText)
        binding.tvEpisodesTitle.setBackgroundColor(helper.DarkMutedBG)

        binding.clCharacter.setBackgroundColor(helper.MutedBG)

        binding.tvGender.setTextColor(helper.MutedText)
        binding.tvGenderDesc.setTextColor(helper.MutedText)

        binding.tvStatus.setTextColor(helper.MutedText)
        binding.tvStatusDesc.setTextColor(helper.MutedText)

        binding.tvCreationDate.setTextColor(helper.MutedText)
        binding.tvCreationDateDesc.setTextColor(helper.MutedText)

        binding.btUbicacion.setTextColor(helper.DarkMutedText)
        binding.tvUbicacionDesc.setTextColor(helper.MutedText)
        binding.btUbicacion.setBackgroundColor(helper.DarkMutedBG)

        binding.btOrigen.setTextColor(helper.DarkMutedText)
        binding.tvOrigenDesc.setTextColor(helper.MutedText)
        binding.btOrigen.setBackgroundColor(helper.DarkMutedBG)

        fillEpisodes(binding.llepisodesChar, character.episode, helper)


    }

    private fun fillEpisodes(ll: LinearLayout, episodes: List<String>, helper: ColorHelper) {
        ll.removeAllViews()
        ll.dividerDrawable = ColorDrawable(helper.DarkMutedBG)
        ll.dividerPadding = 30

        for (episode in episodes) {
            val tv = TextView(requireContext())
            tv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
            viewModel.getSelectedEpisode(episode).observe(viewLifecycleOwner) { episodio ->
                tv.text = episodio.episode.replace("S", "Temporada ").replace("E"," Episodio ") + ":\n" + episodio.name
                tv.setPadding(0,10,0,10)
                tv.setOnClickListener{
                    viewModel.setEpisode(episodio)
                    findNavController().navigate(R.id.action_SecondFragment_to_fragmentEpisode)
                }

            }
            tv.setTextSize(20F)
            tv.setTextColor(helper.MutedText)
            ll.addView(tv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}