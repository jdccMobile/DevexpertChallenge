package com.jdccmobile.devexpertchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jdccmobile.devexpertchallenge.data.RetrofitServiceFactory
import com.jdccmobile.devexpertchallenge.data.model.UnsplashPhotosResult
import com.jdccmobile.devexpertchallenge.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val service = RetrofitServiceFactory.makeRetrofitService()
        val photosRecycler = binding.recycler

        var photos: UnsplashPhotosResult?
        lifecycleScope.launch {
            photos = service.listNewPhotos(getString(R.string.unsplash_client_id), 1, 10)
            Log.d("JDJD", "Photos from API: ${photos.toString()}")
            photosRecycler.adapter = PhotoAdapter(photos)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        // todo add search hint
        return super.onCreateOptionsMenu(menu)
    }

}
