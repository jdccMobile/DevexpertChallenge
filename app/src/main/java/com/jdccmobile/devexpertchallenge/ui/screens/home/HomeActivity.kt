package com.jdccmobile.devexpertchallenge.ui.screens.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jdccmobile.devexpertchallenge.R
import com.jdccmobile.devexpertchallenge.data.PhotosRepository
import com.jdccmobile.devexpertchallenge.data.local.LocalDataSource
import com.jdccmobile.devexpertchallenge.data.local.PhotosDatabase
import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.RemoteDataSource
import com.jdccmobile.devexpertchallenge.databinding.ActivityHomeBinding
import com.jdccmobile.devexpertchallenge.ui.screens.detail.DetailActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var photosRecycler: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val db = Room.databaseBuilder(
            this,
            PhotosDatabase::class.java,
            "photos-db"
        ).build()
        val photosRepository = PhotosRepository(
            localDataSource = LocalDataSource(db.photosDao()),
            remoteDataSource = RemoteDataSource()
        )
        viewModel = viewModels<HomeViewModel> { HomeViewModelFactory(photosRepository) }.value

        initRecyclerView()

        viewModel.state.observe(this) { uiState ->
            setProgressBar(uiState)
            setPhotosAdapter(uiState)
        }
    }

    private fun initRecyclerView() {
        photosRecycler = binding.recycler
        photosRecycler.layoutManager = LinearLayoutManager(this)
        photoAdapter = PhotoAdapter(mutableListOf<Photo?>()) { photo ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("idPhoto", photo?.id)
            startActivity(intent)
        }
        photosRecycler.adapter = photoAdapter
    }

    private fun setProgressBar(uiState: HomeViewModel.UiState) {
        if (uiState.isLoading) {
            binding.adapterProgressBar.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        } else {
            binding.adapterProgressBar.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
        }
    }

    private fun setPhotosAdapter(uiState: HomeViewModel.UiState) {
        photoAdapter.updateData(uiState.photos)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        // todo add search hint
        return super.onCreateOptionsMenu(menu)
    }

}
