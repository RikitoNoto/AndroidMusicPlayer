package com.example.musicplayer

import android.content.ContentUris
import android.content.Intent
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

        setInformation(intent)

        //ここで音楽選択画面から受け取った情報を変数に入れている
        val uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, intent.getLongExtra("ID", 0))//uriの取得

        //プレイヤーの作成。同時に初期化を非同期で行っている。
        //prepareが完了したらスタートボタンが押せるようにリスナの登録も行っている
        musicPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, uri)
            prepareAsync()
            setOnPreparedListener{
                playButton.setOnClickListener{ PlayMusic() }
            }
        }
    }

    fun setInformation(intent: Intent)//画面上の情報のセット
    {
        titleText.text = intent.getStringExtra("TITLE")//曲のタイトルの取得
        artistText.text = intent.getStringExtra("ARTIST")//アーティスト名の取得

    }

    //再生ボタンが押されたときに実行。セットされている音楽を再生
    //押されたときにボタンをポーズボタンに変えて、リスナの変更もしている
    fun PlayMusic()
    {
        musicPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playButton.setOnClickListener{ PauseMusic()}
    }

    //ポーズボタンが押されたときに実行。再生されている音楽を一時停止
    //押されたときにボタンをスタートボタンに変えて、リスナの変更もしている
    fun PauseMusic()
    {
        musicPlayer.pause()
        playButton.setImageResource(R.drawable.play)
        playButton.setOnClickListener{ PlayMusic() }
    }
}
