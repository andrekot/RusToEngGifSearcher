package org.andrekot.rustoenggifsearcher.service

/*Created by Andrekot on 16/10/18*/

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import io.reactivex.Observable
import com.google.gson.annotations.SerializedName
import org.andrekot.rustoenggifsearcher.BuildConfig
import org.andrekot.rustoenggifsearcher.model.GifResult

class GifResponse {
    @SerializedName("data")
    var gifData: List<GifResult>? = null
        private set
}

interface NetService {
    @GET
    fun fetchGifs(@Url url: String): Observable<GifResponse>
}

object GifFactory {

    private val TRENDING_GIFS_ENDPOINT = "gifs/trending"
    private val SEARCH_GIFS_ENDPOINT = "gifs/search"

    private var offset = 0
    var gifsLimit = 10

    fun create(): NetService {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(NetService::class.java)
    }

    fun getTrendingGifs(): String {
        val queryUrl = BuildConfig.API_ENDPOINT + TRENDING_GIFS_ENDPOINT + "?api_key=" + BuildConfig.API_KEY + "&limit=" + gifsLimit +
                "&offset=" + offset + "&rating=G"
        updateOffset()
        return queryUrl
    }

    fun getSearchGifsQueryUrl(query: String): String {
        resetOffset()
        return BuildConfig.API_ENDPOINT + SEARCH_GIFS_ENDPOINT + "?api_key=" + BuildConfig.API_KEY + "&q=" + query + "&limit=" + gifsLimit + "&rating=G&lang=en"
    }

    private fun updateOffset() {
        offset += gifsLimit
    }

    private fun resetOffset() {
        offset = 0
    }
}
