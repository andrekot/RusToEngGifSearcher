package org.andrekot.rustoenggifsearcher.model

import com.google.gson.annotations.SerializedName

class GifResponse {
    @SerializedName("data")
    var gifData: List<GifResult>? = null
        private set
}