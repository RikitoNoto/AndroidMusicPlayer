package com.example.musicplayer

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play_screen.*
import java.io.InputStream


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

        val albumArtUri: Uri = Uri.parse("content://media/external/audio/albumart")

        val alubum_id = intent.getLongExtra("ALBUM_ID", 0L)
        if( alubum_id != 0L)//アルバムなら画像のセット
        {
            val album1Uri: Uri = ContentUris.withAppendedId(albumArtUri, alubum_id)

            val displayMetrics = resources.displayMetrics

            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val cr: ContentResolver = getContentResolver ()
            val inputStream: InputStream = cr.openInputStream(album1Uri)
            val albumArt: Bitmap  = BitmapFactory.decodeStream(inputStream)

            val resizeScale: Double = dpWidth.toDouble() / albumArt.width


            albumImage.setImageBitmap(Bitmap.createScaledBitmap(albumArt, (albumArt.width * resizeScale).toInt(), (albumArt.height * resizeScale).toInt(), true))
        }









    }

    //再生ボタンが押されたときに実行。セットされている音楽を再生
    //押されたときにボタンをポーズボタンに変えて、リスナの変更もしている
    fun PlayMusic()
    {
        musicPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playButton.setOnClickListener{ PauseMusic()}

        val displayMetrics = resources.displayMetrics

        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        Log.d("debug", dpWidth.toString())
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
