package org.andrekot.rustoenggifsearcher.model

/*Created by Andrekot on 16/10/18*/

import com.google.gson.annotations.SerializedName
import java.util.*

class TransResult {

    @SerializedName("code")
    var code: String? = null

    @SerializedName("lang")
    var lang: String? = null

    @SerializedName("text")
    var text: Array<String?> = arrayOf()
}

class ResString: Observable() {
    var resString: String? = null
}