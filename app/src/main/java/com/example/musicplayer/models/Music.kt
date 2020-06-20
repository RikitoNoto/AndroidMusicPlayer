package com.example.musicplayer.models

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class Music(var context: Context, var id: Long, val clickListener: ()-> Unit )
{

    public var album_id: Long?
    public var artist_id: Long
    public var title: String
    public var artist: String
    public var album: String
    init
    {
        var selection: String = "${MediaStore.Audio.Media._ID} = ?"
        var selectionArgs = arrayOf(id.toString())
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        null,
        selection,
        selectionArgs,
        null)
        when {
            cursor == null -> {
                // query failed, handle error.
                throw IllegalArgumentException("無効なIDが入力されています。")
            }
            cursor.moveToFirst() -> {
                // no media on the device
                title = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE))//タイトルのセット
                artist_id = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST_ID))//アーティストIDのセット
                artist = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))//アーティストのセット
                album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))//アルバムIDのセット
                album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))//アルバムのセット
            }
            else -> {
                throw IllegalArgumentException("無効なIDが入力されています。")
            }
        }
        cursor?.close()
    }
}