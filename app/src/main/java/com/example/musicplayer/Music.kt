package com.example.musicplayer

class Music(var id: Long, var title: String, var artist: String, val clickListener: ()-> Unit )
{
}