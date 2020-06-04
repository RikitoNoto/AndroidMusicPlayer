package com.example.musicplayer

import android.content.ContentUris
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_play_screen.*

class PlayScreen : AppCompatActivity() {
    private lateinit var musicPlayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_screen)

        val uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, intent.getLongExtra("id", 0))
        musicPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, uri)
            prepareAsync()
            setOnPreparedListener{
                playButton.setOnClickListener{ PlayMusic() }
            }
        }
    }

    fun PlayMusic()
    {
        musicPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playButton.setOnClickListener{ PauseMusic()}
    }

    fun PauseMusic()
    {
        musicPlayer.pause()
        playButton.setImageResource(R.drawable.play)
        playButton.setOnClickListener{ PlayMusic() }
    }

    fun test()
    {
        val media = MediaStore.Audio.Media()
//        media.getContentUri()
    }
}
