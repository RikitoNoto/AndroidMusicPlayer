package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
//    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        bottomNavigation = findViewById(R.id.footer_menu) as BottomNavigationView
        recyclerView = findViewById(R.id.musicList)

    }
}
