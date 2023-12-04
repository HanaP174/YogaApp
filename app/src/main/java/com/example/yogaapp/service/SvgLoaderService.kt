package com.example.yogaapp.service

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class SvgLoaderService(private val context: Context) {

    fun loadSvgImage(svgUrl: String, imageView: ImageView) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream: InputStream = URL(svgUrl).openStream()

                val svg: SVG = SVG.getFromInputStream(inputStream)

                val pictureDrawable = PictureDrawable(svg.renderToPicture())

                withContext(Dispatchers.Main) {
                    Glide.with(context)
                        .load(pictureDrawable)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }

            } catch (e: SVGParseException) {
                e.printStackTrace()
            }
        }
    }
}