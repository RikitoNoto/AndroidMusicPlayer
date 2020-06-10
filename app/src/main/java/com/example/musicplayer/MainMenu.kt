package com.example.musicplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_music_list.*

class MainMenu : AppCompatActivity() {

    val REQUEST_EXTERNAL_STORAGE: Int = 1
    val PERMISSIONS_STORAGE: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)


    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val permission:Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
        }

        val musicList = createMusicList()

        bottomNavigation = findViewById(R.id.footer_menu) as BottomNavigationView
        viewManager = LinearLayoutManager(this)
        viewAdapter = MusicAdapter(musicList)

        this.musicList.layoutManager = viewManager
        this.musicList.adapter = viewAdapter

    }

    fun createMusicList(): ArrayList<Music>
    {
        var musics: ArrayList<Music> = ArrayList<Music>()
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        when {
            cursor == null -> {
                intent = null
                // query failed, handle error.
            }
            !cursor.moveToFirst() -> {
                // no media on the device
                intent = null
            }
            else -> {
                do {
                    musics.add(createMusic(cursor))
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return musics
    }

    fun createMusic(cursor: Cursor): Music
    {
        val music: Music = Music()
        music.id = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID))//idのセット
        music.title = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE))//タイトルのセット
        music.artist = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))//アーティストのセット
        return music
    }
}
