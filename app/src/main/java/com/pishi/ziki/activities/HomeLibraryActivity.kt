package com.pishi.ziki.activities

import android.content.pm.PackageManager
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pishi.ziki.adapters.AllMusicAdapter
import com.pishi.ziki.dataClass.Music
import com.pishi.ziki.R
import com.pishi.ziki.databinding.ActivityHomeLibraryBinding
import com.pishi.ziki.interfaces.itemClicked

open class HomeLibraryActivity : AppCompatActivity(), itemClicked {

    private lateinit var binding: ActivityHomeLibraryBinding
    private lateinit var musicList: MutableList <Music>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AllMusicAdapter
    private var currentPosition = 0
    private var state = false
    private var mediaPlayer : MediaPlayer? = null

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_library)

        binding = ActivityHomeLibraryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        musicList = mutableListOf()

        checkPermission()

        binding.minimizedPlayButton.setOnClickListener {
            play(currentPosition)
        }

        binding.minimizedNextButton.setOnClickListener{

            mediaPlayer?.stop()
            state = false

            if (currentPosition < musicList.size - 1) {
                currentPosition += 1
                play(currentPosition)

            }else{
                currentPosition = 0
                play(currentPosition)
            }
        }

    }

    private fun play (currentPosition: Int){

        if (!state){
            binding.minimizedPlayButton.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.pause_ic,null))
            state = true

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(this@HomeLibraryActivity, Uri.parse(musicList[currentPosition].songUri))
                prepare()
                start()
            }

        } else{

            state = false
            mediaPlayer?.stop()
            binding.minimizedPlayButton.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.play_ic, null))
        }

    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkPermission(){

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            getSongs()
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(this,"Music Player Needs to Access Your Files", Toast.LENGTH_SHORT).show()

                }
        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
        )

        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getSongs()

            } else {
                Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getSongs(){

        val selection = MediaStore.Audio.Media.IS_MUSIC
        val projection = arrayOf(
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DATA)

        val cursor : Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,
                    null, null)

        while (cursor!!.moveToNext()){
            musicList.add(Music(cursor.getString(0),cursor.getString(1), cursor.getString(2))) //musicName and artistName respectively

        }
        cursor.close()

        linearLayoutManager = LinearLayoutManager(this)
        adapter = AllMusicAdapter(musicList, this)

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter

    }

    override fun itemClicked(position: Int) {

        mediaPlayer?.stop()
        state = false
        this.currentPosition =position
        play(currentPosition)
    }
}