package org.andrekot.rustoenggifsearcher.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.andrekot.rustoenggifsearcher.model.GifResult
import org.andrekot.rustoenggifsearcher.view.DetailActivity

public class ItemViewModel: BaseObservable() {

    private var gifResult: GifResult? = null
    var context: Context? = null

    companion object {

        fun create(gifResult: GifResult, context: Context): ItemViewModel {
            val iVM = ItemViewModel()
            iVM.gifResult = gifResult
            iVM.context = context
            return iVM
        }
    }

    fun getId(): String? {
        return gifResult?.id
    }

    fun getImageUrl(): String? {
        return gifResult?.images?.fixed_height?.url
    }

    object ItemBindingAdapter {
        @JvmStatic
        @BindingAdapter("imageUrl", "progressbar")
        fun setImageUrl(imageView: ImageView?, url: String?, progressBar: ProgressBar) {
            Glide.with(imageView!!.context).load(url).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            }).into(imageView)
        }
    }

    fun onItemClick(view: View) {
        val intent = DetailActivity
                .launch(view.context, gifResult!!)
        context?.startActivity(intent)
    }

    fun setGifResult(gifResult: GifResult) {
        this.gifResult = gifResult
        notifyChange()
    }
}
