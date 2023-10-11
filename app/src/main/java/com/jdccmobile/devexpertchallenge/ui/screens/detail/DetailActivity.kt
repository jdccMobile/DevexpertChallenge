package com.jdccmobile.devexpertchallenge.ui.screens.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.room.Room
import com.jdccmobile.devexpertchallenge.R
import com.jdccmobile.devexpertchallenge.data.PhotosRepository
import com.jdccmobile.devexpertchallenge.data.local.LocalDataSource
import com.jdccmobile.devexpertchallenge.data.local.PhotosDatabase
import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.RemoteDataSource
import com.jdccmobile.devexpertchallenge.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso



class DetailActivity : AppCompatActivity() {

    companion object {
        const val ID_PHOTO = "ID_PHOTO"
        const val COLOR_PHOTO = "COLOR_PHOTO"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var toolbarPhoto: Photo? = null
    private lateinit var colorPhoto: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val idPhoto = intent.getStringExtra(ID_PHOTO)
        colorPhoto = intent.getStringExtra(COLOR_PHOTO)!!
        val db = Room.databaseBuilder(this, PhotosDatabase::class.java, "photos-db").build()
        val photosRepository = PhotosRepository(localDataSource = LocalDataSource(db.photosDao()), remoteDataSource = RemoteDataSource())

        setToolbar()

        viewModel = viewModels<DetailViewModel> { DetailViewModelFactory(photosRepository, idPhoto!!) }.value
        viewModel.state.observe(this) { uiState ->
            toolbarPhoto = uiState.photo

            printPhotoText()
            Picasso.get().load(toolbarPhoto?.urls).into(binding.ivPhotoToolbar)

            setFabFavIcon()
            binding.fabFavorite.setOnClickListener {
                viewModel.onPhotoClick(toolbarPhoto)
                setFabFavIcon()
            }
        }
    }

    private fun setToolbar() {
        supportActionBar?.apply {
            title = getString(R.string.unsplash_com)
            setDisplayShowHomeEnabled(true) // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true) // show back button on toolbar

            val colorId = if (colorPhoto < "#808080") R.color.white // #808080 -> gray
            else R.color.black
            val spannableTitle = SpannableString(title)
            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this@DetailActivity, colorId))
            spannableTitle.setSpan(
                colorSpan,
                0,
                (title as String).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            title = spannableTitle
        }
    }

    private fun printPhotoText() {
        binding.tvPhotoInfo.text = buildSpannedString {
            appendInfo(R.string.description, toolbarPhoto?.description ?: getString(R.string.without_description))
            appendInfo(R.string.username, toolbarPhoto?.userName ?: getString(R.string.anonymous))
            appendInfo(R.string.likes, toolbarPhoto?.likes.toString())
            appendInfo(R.string.size, "${toolbarPhoto?.width} x ${toolbarPhoto?.height}")
            appendInfo(R.string.id, "${toolbarPhoto?.id}")
            appendInfo(R.string.color, colorPhoto)
        }
    }

    private fun setFabFavIcon() {
        if (toolbarPhoto?.isFavorite == true)
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav))
        else
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav_border))
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value: String) {
        bold {
            append(getString(stringRes))
            appendLine(": ")
        }
        appendLine("$value \n")
    }




}