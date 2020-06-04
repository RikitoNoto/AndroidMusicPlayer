package com.example.musicplayer

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    val REQUEST_EXTERNAL_STORAGE: Int = 1
    val PERMISSIONS_STORAGE: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permission:Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
        }

        val (id, title) = searchMusicUrl(contentResolver)
        if(id != null)
            {
                val intent = Intent(this, PlayScreen::class.java)
                intent.putExtra("TITLE", title)
                intent.putExtra("id", id)
                startActivity(intent)
            }
    }

    fun searchMusicUrl(resolver: ContentResolver): Pair<Long?, String?>
    {
        var title: String? = null
        var id: Long? = null
        Log.d("debugLog", "called")
        val cursor:Cursor? = resolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        when {
            cursor == null -> {
                // query failed, handle error.
            }
            !cursor.moveToFirst() -> {
                // no media on the device
            }
            else -> {
                val titleColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
                val idColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
                title = cursor.getString(titleColumn)
                id = cursor.getLong(idColumn)
//                do {
//                    val thisId = cursor.getLong(idColumn)
//                    val thisTitle = cursor.getString(titleColumn)
//                    // ...process entry...
//                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return Pair(id, title)
    }

}
