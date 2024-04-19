package com.juanfra.rickymartin.ui.PicassoThings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.juanfra.rickymartin.R
import com.squareup.picasso.Picasso
import okhttp3.internal.notify
import okhttp3.internal.wait
import java.lang.Exception

class ColorHelper(val imageUrl: String, val context: Context) {

    private lateinit var palette: Palette

    var LightVibrantBG = 0
    var LightVibrantText = 0

    var VibrantBG = 0
    var VibrantText = 0

    var DarkVibrantBG = 0
    var DarkVibrantText = 0

    var LightMutedBG = 0
    var LightMutedText = 0

    var MutedBG = 0
    var MutedText = 0

    var DarkMutedBG = 0
    var DarkMutedText = 0

    fun init() {

        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    palette = Palette.from(it).generate()


                        LightVibrantBG = getBgFromSwatch(palette.lightVibrantSwatch)
                        LightVibrantText = getTextFromSwatch(palette.lightVibrantSwatch)

                        VibrantBG = getBgFromSwatch(palette.vibrantSwatch)
                        VibrantText = getTextFromSwatch(palette.vibrantSwatch)

                        DarkVibrantBG = getBgFromSwatch(palette.darkVibrantSwatch)
                        DarkVibrantText = getTextFromSwatch(palette.darkVibrantSwatch)

                        LightMutedBG = getBgFromSwatch(palette.lightMutedSwatch)
                        LightMutedText = getTextFromSwatch(palette.lightMutedSwatch)

                        MutedBG = getBgFromSwatch(palette.mutedSwatch)
                        MutedText = getTextFromSwatch(palette.mutedSwatch)

                        DarkMutedBG = getBgFromSwatch(palette.darkMutedSwatch)
                        DarkMutedText = getTextFromSwatch(palette.darkMutedSwatch)
                }
            }

            private fun getTextFromSwatch(swatch: Palette.Swatch?): Int {
                return swatch?.titleTextColor ?: ContextCompat.getColor(context, R.color.black)
            }

            private fun getBgFromSwatch(swatch: Palette.Swatch?): Int {
                return swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(context, "Carga de bitmap fallida", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }



        }

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(target)

    }

}