import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.juanfra.rickymartin.R
import com.juanfra.rickymartin.data.models.characterlist.Personaje
import com.juanfra.rickymartin.databinding.HolderPersonajesBinding
import com.juanfra.rickymartin.ui.PicassoThings.ColorHelper
import com.juanfra.rickymartin.ui.MyViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class AdaptadorPersonajes(var listado: ArrayList<Personaje>) :
    RecyclerView.Adapter<AdaptadorPersonajes.MiCelda>() {
    //Your holder here
    inner class MiCelda(var binding: HolderPersonajesBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        lateinit var viewModel: MyViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiCelda {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderPersonajesBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    override fun getItemCount(): Int {
        return listado.size
    }

    override fun onBindViewHolder(holder: MiCelda, position: Int) {
        val personaje = listado[position]

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
            } else {
                Picasso.get()
                    .load(personaje.image)
                    .into(ivPersonajeLista)
                tvDead.visibility = TextView.GONE
            }


            tvNombrePersonajeLista.text = personaje.name

            tvNombrePersonajeLista.setBackgroundColor(helper.MutedBG)
            tvNombrePersonajeLista.setTextColor(helper.MutedText)
            cardview.setCardBackgroundColor(helper.MutedBG)
        }

        holder.itemView.setOnClickListener{
            viewModel.setCharacter(personaje.id)
            Navigation.findNavController(holder.itemView).navigate(R.id.initialFragment_to_Second_Fragment)
        }
    }


}