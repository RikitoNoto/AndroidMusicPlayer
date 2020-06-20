package com.example.musicplayer.models

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class Album(var context: Context, var id: Long, val clickListener: ()-> Unit )
{
//    public var artist_id: Long
    public var title: String
    public var artist: String
    public var musics_ids: ArrayList<Long>
    public var number_of_songs: Int
    init
    {
        var selection: String = "${MediaStore.Audio.Albums._ID} = ?"
        var selectionArgs = arrayOf(id.toString())
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            null,
            selection,
            selectionArgs,
            MediaStore.Audio.Albums.DEFAULT_SORT_ORDER)
        when {
            cursor == null -> {
                // query failed, handle error.
                throw IllegalArgumentException("無効なIDが入力されています。")
            }
            cursor.moveToFirst() -> {
                // no media on the device
                title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM))//タイトルのセット
//                artist_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST_ID))//アーティストIDのセット
                artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST))//アーティストのセット
                number_of_songs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS)).toInt()//アルバムの曲数のセット
                musics_ids = get_musics()
            }
            else -> {
                throw IllegalArgumentException("無効なIDが入力されています。")
            }
        }
        cursor?.close()
    }

    fun get_musics(): ArrayList<Long>
    {
        var music_ids: ArrayList<Long> = ArrayList<Long>()
        var selection: String = "${MediaStore.Audio.Media.ALBUM_ID} = ?"
        var selectionArgs = arrayOf(id.toString())
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            selection,
            selectionArgs,
        MediaStore.Audio.Media.DEFAULT_SORT_ORDER)

        if(cursor != null)
        {
            cursor.moveToFirst()
            do {
                music_ids.add(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)))
            }while (cursor.moveToNext())
        }
        return musics_ids
    }
}