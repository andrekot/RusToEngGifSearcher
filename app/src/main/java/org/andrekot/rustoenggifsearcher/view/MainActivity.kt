package org.andrekot.rustoenggifsearcher.view

/*
 * Russian Gif Searcher using Yandex Translate Api, RxJava, Retrofit, Glide, etc...
 * Created by Andrekot on 16/10/18
 * @author Andrew Martynets - Andrekot
 */

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import org.andrekot.rustoenggifsearcher.R
import org.andrekot.rustoenggifsearcher.service.GifFactory
import org.andrekot.rustoenggifsearcher.databinding.ActivityMainBinding
import org.andrekot.rustoenggifsearcher.model.ResString
import org.andrekot.rustoenggifsearcher.viewmodel.MainViewModel
import java.util.Observable
import java.util.Observer

class MainActivity : AppCompatActivity(), Observer {

    private var mainActivityBinding: ActivityMainBinding? = null
    private var mainViewModel: MainViewModel? = null
    private var searchString = ResString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "RTESearcher"
        toolbar.setNavigationOnClickListener { mainViewModel!!.revertGifsDataSet() }

        setupListGifView(mainActivityBinding!!.listGif)
        setupObserver(mainViewModel)
        setupObserver(searchString)
    }

    private fun initDataBinding() {
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        mainActivityBinding!!.mainViewModel = mainViewModel
    }

    private fun setupListGifView(recyclerView: RecyclerView) {
        val adapter = GifAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObserver(observable: Observable?) {
        observable!!.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel!!.reset()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryRus: String): Boolean {
                mainViewModel!!.gifProgress.set(View.VISIBLE)
                mainViewModel!!.gifRecycler.set(View.GONE)
                mainViewModel!!.clearGifsDataSet()
                mainViewModel!!.fetchGifList(queryRus)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return true
            android.R.id.home -> {
                mainViewModel!!.revertGifsDataSet()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun update(observable: Observable?, data: Any?) {
        if (observable is MainViewModel) {
            if (mainActivityBinding != null) {
                val gifAdapter = mainActivityBinding!!.listGif.adapter as GifAdapter
                val gifViewModel = observable
                gifAdapter.setGifList(gifViewModel.getGifList())
            }
        }
        if (observable is ResString) {
            mainViewModel!!.fetchGifList(GifFactory.getSearchGifsQueryUrl(observable.resString.toString()))
        }
    }
}
