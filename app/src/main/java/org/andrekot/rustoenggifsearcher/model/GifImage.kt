package org.andrekot.rustoenggifsearcher.model

/*Created by Andrekot on 16/10/18*/

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GifImage: Serializable {

    @SerializedName("url")
    var url: String? = null

    @SerializedName("width")
    var width: Int = 0

    @SerializedName("height")
    var height: Int = 0

    @SerializedName("size")
    var size: Int = 0

}
