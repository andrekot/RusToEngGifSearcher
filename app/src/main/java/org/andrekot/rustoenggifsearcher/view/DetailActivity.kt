package org.andrekot.rustoenggifsearcher.view

/*Created by Andrekot on 16/10/18*/

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import org.andrekot.rustoenggifsearcher.R
import org.andrekot.rustoenggifsearcher.model.GifResult
import org.andrekot.rustoenggifsearcher.databinding.ActivityDetailBinding

import kotlinx.android.synthetic.main.activity_detail.*
import org.andrekot.rustoenggifsearcher.viewmodel.DetailViewModel

class DetailActivity: AppCompatActivity() {

    private val EXTRA_GIF = "EXTRA_GIF"

    private var gifDetailActivityBinding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        gifDetailActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener{ onBackPressed() }
        displayHomeAsUpEnabled()
        getExtrasFromIntent()
    }

    fun launchActivity(context: Context, gifResult: GifResult): Intent {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_GIF, gifResult)
        return intent
    }

    companion object {
        @JvmStatic
        private val instance = DetailActivity()
        fun launch(context: Context, gifResult: GifResult) = instance.launchActivity(context, gifResult)
    }

    private fun displayHomeAsUpEnabled() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getExtrasFromIntent() {
        val gifResult = intent.getSerializableExtra(EXTRA_GIF) as GifResult
        val gifDetailViewModel = DetailViewModel(gifResult, applicationContext)
        gifDetailActivityBinding?.setGifDetailViewModel(gifDetailViewModel)
        title = gifResult.title
    }

}
