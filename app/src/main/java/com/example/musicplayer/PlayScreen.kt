package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play_screen.*

class PlayScreen : AppCompatActivity() {
    private lateinit var music : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_screen)

        music = MediaPlayer.create(this, intent.getIntExtra("MUSIC", 0))
        playButton.setOnClickListener{ PlayMusic() }
    }

    fun PlayMusic()
    {
        music.start()
        playButton.setImageResource(R.drawable.pause)
        playButton.setOnClickListener{ PauseMusic()}
    }

    fun PauseMusic()
    {
        music.pause()
        playButton.setImageResource(R.drawable.play)
        playButton.setOnClickListener{ PlayMusic() }
    }
}
