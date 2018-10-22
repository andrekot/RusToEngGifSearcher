package org.andrekot.rustoenggifsearcher.viewmodel

import android.app.Activity
import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import com.google.gson.Gson
import io.reactivex.Single
import org.andrekot.rustoenggifsearcher.R
import org.andrekot.rustoenggifsearcher.service.GifFactory
import org.andrekot.rustoenggifsearcher.model.GifResult
import java.util.ArrayList
import java.util.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.andrekot.rustoenggifsearcher.RTEApplication
import org.andrekot.rustoenggifsearcher.model.TransResult
import org.andrekot.rustoenggifsearcher.service.Translate
import java.net.URL

class MainViewModel(private var context: Context?) : Observable() {

    var gifProgress: ObservableInt
    var gifRecycler: ObservableInt
    var gifLabel: ObservableInt
    var gifFab: ObservableInt
    var messageLabel: ObservableField<String>

    private val gifList: MutableList<GifResult>
    private val oldGifList: MutableList<GifResult>
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var gson: Gson? = null

    private val isNetworkConnected: Boolean
        get() {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

    init {
        gifList = ArrayList()
        oldGifList = ArrayList()
        gifProgress = ObservableInt(View.GONE)
        gifRecycler = ObservableInt(View.GONE)
        gifLabel = ObservableInt(View.VISIBLE)
        gifFab = ObservableInt(View.VISIBLE)
        messageLabel = ObservableField(context!!.getString(R.string.default_loading))
    }

    fun onClickFabLoad(view: View) {
        initializeViews()
        fetchGifList(GifFactory.getTrendingGifs())
    }

    private fun initializeViews() {
        gifLabel.set(View.GONE)
        gifRecycler.set(View.GONE)
        gifProgress.set(View.VISIBLE)
        gifFab.set(View.GONE)
    }

    fun fetchTranslate(queryUrl: String): String {

        return URL(queryUrl).readText()
    }

    fun fetchGifList(queryUrl: String) {

        if (!isNetworkConnected) {
            Snackbar.make((context as Activity).window.decorView, R.string.no_inet, Snackbar.LENGTH_LONG).show()
        }

        val gifApplication = RTEApplication.create(context!!)
        val gifService = gifApplication.service

        val urlRus = Translate.getTranslateResult(queryUrl)

        val sq = Single.fromCallable { fetchTranslate(urlRus) }
               .subscribeOn(gifApplication.subscribeScheduler())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe { it ->
                   if (gson == null) gson = Gson()
                   val ret = gson?.fromJson<TransResult>(it, TransResult::class.java)
                   val ss = ret?.text!![0]
                   if (ss != null) {
                       val query = GifFactory.getSearchGifsQueryUrl(ss)
                       val disposable = gifService!!.fetchGifs(query)
                               .subscribeOn(gifApplication.subscribeScheduler())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe({
                                   if (it.gifData?.size == 0) {
                                       messageLabel.set(context!!.getString(R.string.no_matches))
                                       gifLabel.set(View.VISIBLE)
                                       gifRecycler.set(View.GONE)
                                   } else {
                                       changeGifsDataSet(it.gifData!!)
                                       gifProgress.set(View.GONE)
                                       gifLabel.set(View.GONE)
                                       gifRecycler.set(View.VISIBLE)
                                       gifFab.set(View.VISIBLE)
                                   }
                               }, {
                                   messageLabel.set(context!!.getString(R.string.error_message))
                                   gifProgress.set(View.GONE)
                                   gifLabel.set(View.VISIBLE)
                                   gifRecycler.set(View.GONE)
                                   gifFab.set(View.VISIBLE)
                               })

                       compositeDisposable!!.add(disposable)
                   }
               }
    }

    private fun changeGifsDataSet(gifResults: List<GifResult>) {
        gifList.addAll(gifResults)
        setChanged()
        notifyObservers()
    }

    fun clearGifsDataSet() {
        oldGifList.addAll(gifList)
        gifList.clear()
        setChanged()
        notifyObservers()
    }

    fun revertGifsDataSet() {
        gifList.addAll(oldGifList)
        setChanged()
        notifyObservers()
    }

    fun getGifList(): List<GifResult> {
        return gifList
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
        }
    }

    fun reset() {
        unSubscribeFromObservable()
        compositeDisposable = null
        context = null
    }
}
