package org.andrekot.rustoenggifsearcher

/*
 * Russian Gif Searcher using Yandex Translate Api, RxJava2, Retrofit2, Glide, etc...
 * Created by Andrekot on 16/10/18
 * @author Andrew Martynets - Andrekot
 */

import android.app.Application
import android.content.Context
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.andrekot.rustoenggifsearcher.service.GifFactory
import org.andrekot.rustoenggifsearcher.service.NetService

class RTEApplication : Application() {

    var service: NetService? = null
        get() {
            if (field == null) {
                service = GifFactory.create()
            }

            return field
        }

    private var scheduler: Scheduler? = null

    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }

        return scheduler
    }

    companion object {

        private operator fun get(context: Context): RTEApplication {
            return context.applicationContext as RTEApplication
        }

        fun create(context: Context): RTEApplication {
            return RTEApplication[context]
        }
    }
}
