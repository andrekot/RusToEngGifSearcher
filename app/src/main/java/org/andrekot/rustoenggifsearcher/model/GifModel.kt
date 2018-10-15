package org.andrekot.rustoenggifsearcher.model

/*Created by Andrekot on 16/10/18*/

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GifResult : Serializable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("images")
    var images: GifUrlSet? = null

    @SerializedName("title")
    var title: String? = null
}