package com.example.musicplayer.models

class Artist(var id: Long, var title: String, var artist: String, val clickListener: ()-> Unit )
{
}