package org.andrekot.rustoenggifsearcher.service

/*Created by Andrekot on 16/10/18*/

import org.andrekot.rustoenggifsearcher.BuildConfig

object Translate {

    fun getTranslateResult(query: String): String {
        return BuildConfig.TRANSLATE_API_ENDPOINT + "translate?key=" + BuildConfig.TRANSLATE_API_KEY + "&text=" + query + "&lang=ru-en"
    }
}