package com.ydh.phonebookmvvm.view

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ydh.phonebookmvvm.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val colorDrawable = ColorDrawable(resources.getColor(R.color.bg_main))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
       supportActionBar?.elevation = 0.0F
        supportActionBar?.title = ""
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}