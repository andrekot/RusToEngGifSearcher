package org.andrekot.rustoenggifsearcher.model

/*Created by Andrekot on 16/10/18*/

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifResult(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("images")
    var images: GifUrlSet? = null,

    @SerializedName("title")
    var title: String? = null

): Parcelable