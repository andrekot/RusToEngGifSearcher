package org.andrekot.rustoenggifsearcher.model

/*Created by Andrekot on 16/10/18*/

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GifUrlSet : Serializable {

    @SerializedName("original")
    var original: GifImage? = null

    @SerializedName("fixed_width")
    var fixed_width: GifImage? = null

    @SerializedName("fixed_height")
    var fixed_height: GifImage? = null
}
