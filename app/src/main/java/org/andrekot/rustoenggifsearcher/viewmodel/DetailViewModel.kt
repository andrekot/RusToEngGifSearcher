package org.andrekot.rustoenggifsearcher.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.databinding.BindingAdapter
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.andrekot.rustoenggifsearcher.R
import org.andrekot.rustoenggifsearcher.model.GifResult

class DetailViewModel(private val gifResult: GifResult, private val context: Context) {

    val id: String?
        get() = gifResult.id

    val gifUrl: String?
        get() = gifResult.images?.fixed_height?.url

    val gifWidth: String
        get() = "Width " + gifResult.images?.fixed_height?.width

    val gifHeight: String
        get() = "Height " + gifResult.images?.fixed_height?.height

    val gifSize: String
        @SuppressLint("DefaultLocale")
        get() = String.format("Size %.2f Mb", convertToMb(gifResult.images!!.fixed_height!!.size))

    fun onShareFabClicked(view: View) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, gifResult.images?.original?.url)
            shareIntent.type = "text/plain"
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Snackbar.make(view, R.string.error_message, Snackbar.LENGTH_LONG).show()
        }

    }

    companion object {

        fun convertToMb(bytes: Int): Double {
            return bytes.toDouble() / (1024L * 1024L)
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, imageUrl: String) {
            Glide.with(view.context).load(imageUrl).into(view)
        }
    }
}